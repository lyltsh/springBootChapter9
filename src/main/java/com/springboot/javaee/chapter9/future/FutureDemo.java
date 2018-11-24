package com.springboot.javaee.chapter9.future;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/11/2310:17 AM
 */
public class FutureDemo {

    @Test
    public void testGetPrice() {
        Shop shop = new Shop("GoodShop");
        long start = System.currentTimeMillis();
        double price = shop.getPrice("macBook");
        System.out.printf(shop.getName() + ", price is %.2f%n", price);
        long end = System.currentTimeMillis();
        System.out.println("time cost:" + (end - start));

        doSomethingElse();

        long retrievalTime = System.currentTimeMillis() - start;
        System.out.println("同步方法返回价格所需时间: --- " + retrievalTime + " ---msecs");

    }

    @Test
    public void testAsync(){
        Shop shop = new Shop("GoodShop");
        long start = System.currentTimeMillis();
        Future<Double> futurePrice = shop.getPriceAsync("macBook");
        long end = System.currentTimeMillis();
        System.out.println("time cost:" + (end - start));

        doSomethingElse();

        try {
            Double price = futurePrice.get();
            System.out.printf(shop.getName() + ", price is %.2f%n", price);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        long retrievalTime = System.currentTimeMillis() - start;
        System.out.println("异步方法返回价格所需时间: --- " + retrievalTime + " ---msecs");
    }

    private static void doSomethingElse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
