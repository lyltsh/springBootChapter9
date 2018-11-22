package com.springboot.javaee.chapter9.callable;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/11/2210:27 PM
 */
public class GuavaFutureDemo {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("等待耗时操作.....");
                timeConsuming();
                return 100;
            }
        });

        Futures.addCallback(future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println("计算结果:" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("异步处理失败," + t);
            }
        });
        System.out.println("主线程运算耗时:" + (System.currentTimeMillis() - start) + " ms");
        new CountDownLatch(1).await();//不让守护线程退出
    }

    private static void timeConsuming() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
