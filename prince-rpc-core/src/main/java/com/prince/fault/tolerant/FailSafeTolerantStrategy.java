package com.prince.fault.tolerant;

import com.prince.model.RpcResponse;

import java.util.Map;

public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        return new RpcResponse();
    }
}
