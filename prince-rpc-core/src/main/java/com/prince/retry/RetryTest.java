package com.prince.retry;

import com.prince.model.RpcResponse;
import lombok.SneakyThrows;

public class RetryTest {

    @SneakyThrows
    public static void main(String[] args) {
        RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();
        RpcResponse retry = retryStrategy.retry(() -> {
            System.out.println("retry");
            throw new RuntimeException("error");
        });
        System.out.println(retry);
    }
}
