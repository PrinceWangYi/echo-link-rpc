package com.prince.bootstrap;

import com.prince.RpcApplication;
import com.prince.config.RpcConfig;
import com.prince.model.ServiceMetaInfo;
import com.prince.model.ServiceRegisterInfo;
import com.prince.registry.LocalRegistry;
import com.prince.registry.Registry;
import com.prince.registry.RegistryFactory;
import com.prince.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 */
public class ProviderBootstrap {

    public static void init(List<ServiceRegisterInfo<?>> registerInfoList) {
        RpcApplication.init();
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        for (ServiceRegisterInfo<?> serviceRegisterInfo : registerInfoList) {
            LocalRegistry.register(serviceRegisterInfo.getServiceName(), serviceRegisterInfo.getImplClass());
            Registry registry = RegistryFactory.getRegistry(rpcConfig.getRegistryConfig().getRegistry());

            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceRegisterInfo.getServiceName());
            serviceMetaInfo.setServiceHost(rpcConfig.getHost());
            serviceMetaInfo.setServicePort(rpcConfig.getPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException("注册失败！！！", e);
            }

        }

        new VertxTcpServer().doStart(rpcConfig.getPort());
    }

}
