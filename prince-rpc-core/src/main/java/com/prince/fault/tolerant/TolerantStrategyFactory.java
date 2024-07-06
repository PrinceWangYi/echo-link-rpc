package com.prince.fault.tolerant;

import com.prince.fault.retry.RetryStrategy;
import com.prince.spi.SpiLoader;

public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    public static TolerantStrategy getInstance(String strategyName) {
        return SpiLoader.getInstance(TolerantStrategy.class, strategyName);
    }
}
