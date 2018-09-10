package com.springboot.javaee.chapter9.callable;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author: leiyulin
 * @description: 测试callable和future两个接口
 * @date:2018/9/10下午5:48
 */
public class CallableAndFuture {
    public static void main(String[] args) {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(1000);
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        try {
            Thread.sleep(5000);
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
