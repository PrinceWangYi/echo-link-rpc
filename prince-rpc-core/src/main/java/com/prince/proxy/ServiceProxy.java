package com.prince.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.prince.RpcApplication;
import com.prince.config.RpcConfig;
import com.prince.constant.RpcConstant;
import com.prince.fault.tolerant.TolerantStrategy;
import com.prince.fault.tolerant.TolerantStrategyFactory;
import com.prince.loadbalancer.LoadBalancer;
import com.prince.loadbalancer.LoadBalancerFactory;
import com.prince.model.RpcRequest;
import com.prince.model.RpcResponse;
import com.prince.model.ServiceMetaInfo;
import com.prince.registry.Registry;
import com.prince.registry.RegistryFactory;
import com.prince.fault.retry.RetryStrategy;
import com.prince.fault.retry.RetryStrategyFactory;
import com.prince.serialize.Serializer;
import com.prince.serialize.SerializerFactory;
import com.prince.server.tcp.VertxTpcClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getRegistry(rpcConfig.getRegistryConfig().getRegistry());

        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);

        List<ServiceMetaInfo> serviceMetaInfos = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());

        if (CollUtil.isEmpty(serviceMetaInfos)) {
            throw new RuntimeException("暂无服务地址");
        }

        // 选择服务, 负载均衡
        LoadBalancer balancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("requestParams", rpcRequest.getMethodName());
        ServiceMetaInfo metaInfo = balancer.select(requestParams, serviceMetaInfos);

        // 重试机制
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.retry(() -> VertxTpcClient.doRequest(rpcRequest, metaInfo));
        } catch (Exception e) {
            TolerantStrategy tolerant = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerant.doTolerant(null, e);
        }
        return rpcResponse.getData();
    }

    /**
     * http版本
     */
    private static RpcResponse doHttpRequest(ServiceMetaInfo selectedServiceMetaInfo, RpcRequest rpcRequest) throws IOException {
        final Serializer serializer = SerializerFactory.getSerializer(RpcApplication.getRpcConfig().getSerializer());
        // 发送 HTTP 请求
        try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                .body(serializer.serialize(rpcRequest))
                .execute()) {
            byte[] result = httpResponse.bodyBytes();
            // 反序列化
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse;
        }
    }
}
