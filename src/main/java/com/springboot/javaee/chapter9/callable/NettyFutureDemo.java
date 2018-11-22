package com.springboot.javaee.chapter9.callable;

import io.netty.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author: leiyulin
 * @description: ref: https://cloud.tencent.com/developer/article/1110576
 * @date:2018/11/2210:21 PM
 */
public class NettyFutureDemo {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        EventExecutorGroup group = new DefaultEventExecutorGroup(4);

        Future<Integer> future = group.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("等待耗时操作.....");
                timeConsuming();
                return 100;
            }
        });

        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                System.out.println("计算结果：" + future.get());
            }
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
