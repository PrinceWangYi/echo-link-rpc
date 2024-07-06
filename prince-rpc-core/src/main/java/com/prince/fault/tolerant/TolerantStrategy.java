package com.prince.fault.tolerant;

import com.prince.model.RpcResponse;

import java.util.Map;

/**
 * 容错机制
 */
public interface TolerantStrategy {

    RpcResponse doTolerant(Map<String, Object> context, Exception e);

}
