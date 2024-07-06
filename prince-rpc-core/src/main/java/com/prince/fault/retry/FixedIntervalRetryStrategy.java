package com.prince.fault.retry;

import com.github.rholder.retry.*;
import com.prince.model.RpcResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 固定时间间隔 - 重试策略
 */
public class FixedIntervalRetryStrategy implements RetryStrategy {
    @Override
    public RpcResponse retry(Callable<RpcResponse> callable) throws Exception {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        System.out.println("Retrying..." + attempt.getAttemptNumber());
                    }
                }).build();
        return retryer.call(callable);
    }
}
