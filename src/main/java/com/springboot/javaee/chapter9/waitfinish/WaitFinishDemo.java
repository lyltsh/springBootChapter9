package com.springboot.javaee.chapter9.waitfinish;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/9/309:17 AM
 */
public class WaitFinishDemo {
    public static void main(String[] args) {
        List<Callable> callables = new ArrayList<>();
        final int num = 100;
        for (int i = 0; i < 100; i++) {
            callables.add(() -> calculate(num));
        }
        try {
            for (Future future : new ConcurrentTask(callables).waitFinish()) {
                Object result = future.get();
                if (result == null) {
                    System.out.println("result null");
                    continue;
                }
                System.out.println(Thread.currentThread().getName() + ", " + System.currentTimeMillis() + ", " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static double calculate(int num) {
        int sum = 0;
        for (int i = -num; i < num; i++) {
            sum += Math.abs(i);
        }
        return Math.sin(sum);
    }
}
