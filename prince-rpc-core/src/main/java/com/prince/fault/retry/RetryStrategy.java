package com.prince.fault.retry;

import com.prince.model.RpcResponse;

import java.util.concurrent.Callable;

public interface RetryStrategy {

    RpcResponse retry(Callable<RpcResponse> callable) throws Exception;

}
