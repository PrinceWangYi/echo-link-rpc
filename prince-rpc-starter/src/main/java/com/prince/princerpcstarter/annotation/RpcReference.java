package com.prince.princerpcstarter.annotation;

import com.prince.constant.RpcConstant;
import com.prince.fault.retry.RetryStrategyKeys;
import com.prince.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcReference {

    /**
     * 接口服务类
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本号
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡策略
     */
    String loadBalancer() default LoadBalancerKeys.CONSISTENT_HASH;

    /**
     * 重试策略
     */
    String retry() default RetryStrategyKeys.FIXED_INTERVAL;

    /**
     * 虚拟数据
     */
    boolean mock() default false;

}
