package com.springboot.javaee.chapter9.future;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * @author: leiyulin
 * @description: https://blog.csdn.net/itguangit/article/details/78624404
 * @date:2018/11/2310:57 AM
 */
public class ClientTest {
    List<Shop> shops;
    List<Shop> discountShops;

    @Before
    public void before() {
        shops = Arrays.asList(new Shop("淘宝"),
                new Shop("tianmao"),
                new Shop("amazon"),
                new Shop("jingdong"),
                new Shop("dangdang"));
        discountShops = shops;
    }

    public List<String> findPrice(String product) {
        List<String> list = shops.stream()
                .map(shop -> String.format("%s price is %.2f RMB", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());

        return list;
    }

    /*
    使用并行流计算
     */
    public List<String> findPrice2(String product) {
        List<String> list = shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f RMB", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());

        return list;
    }

    /*
    使用completableFuture异步调用
     */
    public List<String> findPrice3(String product) {
        List<CompletableFuture<String>> futureList = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f RMB", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        List<String> list = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return list;
    }

    /*
    使用定制的executor配置completableFuture异步调用
     */
    public List<String> findPrice4(String product) {
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                //使用守护线程，这种方式不会阻止程序的关停
                thread.setDaemon(true);
                return thread;
            }
        });

        List<CompletableFuture<String>> futureList = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f RMB", shop.getName(), shop.getPrice(product)), executor))
                .collect(Collectors.toList());
        List<String> list = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return list;
    }

    @Test
    public void test() {
        long start = System.nanoTime();

        List<String> list = findPrice1("iphone666s");

        System.out.println(list);
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " ms");
    }


    public List<String> findPrice1(String pruduct) {
        List<String> list = discountShops.stream()
                .map(discountShop -> discountShop.getPrice2(pruduct))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
        return list;
    }

    public List<String> findPrice5(String product) {
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                //使用守护线程，这种方式不会阻止程序的关停
                thread.setDaemon(true);
                return thread;
            }
        });

        List<CompletableFuture<String>> futureList = discountShops.stream()
                .map(discountShop -> CompletableFuture.supplyAsync(() -> discountShop.getPrice2(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
                        //使用另一个异步任务访问折扣服务
                        () -> Discount.applyDiscount(quote), executor)))
                .collect(Collectors.toList());
        List<String> list = futureList.stream()
                //join像get方法，但是不会抛出异常
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return list;
    }


}
