package com.springboot.javaee.chapter9.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/9/10下午6:00
 */
public class CallableAndFutureList {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Callable> callables = new ArrayList<>();
        List<Future> futureList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futureList.add(executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return new Random().nextInt(1000);
                }
            }));
        }

        for (Future future : futureList) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
