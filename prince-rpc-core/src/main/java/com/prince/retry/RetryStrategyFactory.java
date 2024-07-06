package com.prince.retry;

import com.prince.spi.SpiLoader;

public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    public static RetryStrategy getInstance(String strategyName) {
        return SpiLoader.getInstance(RetryStrategy.class, strategyName);
    }
}
