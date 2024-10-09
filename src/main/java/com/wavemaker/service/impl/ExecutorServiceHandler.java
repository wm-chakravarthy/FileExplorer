package com.wavemaker.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceHandler {
    private static volatile ExecutorService executorService;

    public static ExecutorService getExecutorServiceInstance() {
        if (executorService == null) {
            synchronized (ExecutorServiceHandler.class) {
                if (executorService == null) {
                    executorService = Executors.newFixedThreadPool(10);
                }
            }
        }
        return executorService;
    }
}
