package com.prince.config;

import com.prince.fault.tolerant.TolerantStrategyKeys;
import com.prince.loadbalancer.LoadBalancerKeys;
import com.prince.fault.retry.RetryStrategyKeys;
import com.prince.serialize.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {

    private String rpcName = "prince-rpc";

    private String version = "1.0";

    private String host = "localhost";

    private Integer port = 8080;

    private boolean mock = false;

    private String serializer = SerializerKeys.JDK;

    private RegistryConfig registryConfig = new RegistryConfig();

    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    private String retryStrategy = RetryStrategyKeys.FIXED_INTERVAL;

    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;

}
