package com.springboot.javaee.chapter9.callable;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/9/10下午5:56
 */
public class CallableAndFutureExecute {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(1000);
            }
        };

        Future<Integer> future = threadPool.submit(callable);
        try {
            Thread.sleep(5000);
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
