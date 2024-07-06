package com.prince.fault.tolerant;

import com.prince.model.RpcResponse;

import java.util.Map;

public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("fail fast容错机制", e);
    }
}
