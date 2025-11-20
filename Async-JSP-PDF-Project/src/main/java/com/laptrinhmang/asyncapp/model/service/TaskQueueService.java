package com.laptrinhmang.asyncapp.model.service;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class TaskQueueService{
	/*
	private static final int CPU_CORES = Runtime.getRuntime().availableProcessors();
    private static final int THREAD_POOL_SIZE = CPU_CORES * 2;
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	 */
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(4);
	
	public static void submit(Runnable Worker) {
		EXECUTOR.submit(Worker);
	}
	
	public static void shutdown() {
		EXECUTOR.shutdown();
		try {
			if (!EXECUTOR.awaitTermination(60, TimeUnit.SECONDS)) {
				EXECUTOR.shutdownNow();
			}
		}catch(InterruptedException e) {
			EXECUTOR.shutdownNow();
            Thread.currentThread().interrupt();
		}
	}
}