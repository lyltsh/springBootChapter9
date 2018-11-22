package com.springboot.javaee.chapter9.callable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * @author: leiyulin
 * @description: Promise test
 * @date:2018/11/229:56 PM
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("等待耗时操作.....");
            timeConsuming();
            return 100;
        });

        completableFuture = completableFuture.thenCompose(i -> {
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("在回调的回调中执行耗时操作.....");
                timeConsuming();
                return i + 100;
            });
        });

        completableFuture.whenComplete((result, e) -> {
            System.out.println("结果：" + result);
        });
        System.out.println("主线程耗时：" + (System.currentTimeMillis() - start) + " ms");
        new CountDownLatch(1).await();
    }

    private static void timeConsuming() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
