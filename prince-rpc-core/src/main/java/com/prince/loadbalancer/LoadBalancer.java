package com.prince.loadbalancer;

import com.prince.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡接口
 */
public interface LoadBalancer {

    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);

}
