package com.prince.loadbalancer;

import com.prince.spi.SpiLoader;

public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    public static LoadBalancer getInstance(String loadBalancerName) {
        return SpiLoader.getInstance(LoadBalancer.class, loadBalancerName);
    }
}
