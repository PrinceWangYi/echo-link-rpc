package com.prince.loadbalancer;

import com.prince.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡接口
 */
public interface LoadBalancerKeys {

    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";

}
