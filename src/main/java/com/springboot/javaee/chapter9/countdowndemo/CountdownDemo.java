package com.springboot.javaee.chapter9.countdowndemo;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: leiyulin
 * @description: ref: https://blog.csdn.net/asdasd3418/article/details/77172473
 * https://www.cnblogs.com/xubiao/p/7785042.html
 * <p>
 * 3.CountDownLatch的使用场景
 * 确保某个计算在其需要的所有资源都被初始化之后才继续执行。
 * 确保某个服务在其依赖的所有其他服务都已启动后才启动。
 * 等待知道某个操作的所有者都就绪在继续执行。
 * @date:2018/9/21上午11:18
 */
public class CountdownDemo {

    public static void main(String[] args) {
        CountdownTest();
    }

    public static void CountdownTest() {
        /*
        模拟高并发情况代码
         */

        final AtomicInteger atomicInteger = new AtomicInteger(0);
        // 相当于计数器，当所有都准备好了，再一起执行，模仿多并发，保证并发量
        final CountDownLatch countDownLatch1 = new CountDownLatch(100);
        // 保证所有线程执行完了再打印atomicInteger的值
        final CountDownLatch countDownLatch2 = new CountDownLatch(20);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            for (int i = 0; i < 1000; i++) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //一直阻塞当前线程，直到计时器的值为0,保证同时并发
                            countDownLatch1.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //每次线程增加1000，每次增加1
                        for (int j = 0; j < 1000; j++) {
                            atomicInteger.incrementAndGet();
                        }
                        countDownLatch2.countDown();
                    }
                });
                countDownLatch1.countDown();
            }

            //保证所有线程执行完成
            countDownLatch2.await();
            System.out.println(atomicInteger);
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
