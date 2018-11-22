package com.springboot.javaee.chapter9.blockqueue;

import java.util.concurrent.*;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/10/185:24 PM
 */
public class ThreadPoolTest implements Runnable {
    public void run() {
        synchronized (this) {
            try {
                System.out.println("线程名称：" + Thread.currentThread().getName());
                Thread.sleep(5000); //休眠是为了让该线程不至于执行完毕后从线程池里释放
                System.out.println("after sleep 线程名称：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(4); //固定为4的线程队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 6, 1, TimeUnit.DAYS, queue);
        for (int i = 0; i < 11; i++) {
            executor.execute(new Thread(new ThreadPoolTest(), "TestThread".concat("" + i)));
            int threadSize = queue.size();
            System.out.println("线程队列大小为-->" + threadSize);
        }
        executor.shutdown();
    }

}
