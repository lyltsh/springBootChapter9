package com.springboot.javaee.chapter9.future;

import lombok.Data;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/11/2310:09 AM
 */
@Data
public class Shop {
    private String name;
    private Random random = new Random();

    public Shop(String name) {
        this.name = name;
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * (阻塞式)通过名称查询价格
     *
     * @param product
     * @return 返回  Shop-Name:price:DiscountCode 的格式字符串
     */
    public String getPrice2(String product) {

        double price = calculatePrice(product);
        //随机得到一个折扣码
        Discount.Code code = Discount.Code.values()[
                random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) * product.charAt(1);
    }

    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Future<Double> getPriceAsync(String product) {
//        CompletableFuture<Double> future = new CompletableFuture<>();
//        new Thread(()->{
//            double price = calculatePrice(product);
//            //需要长时间计算的任务结束并返回结果时，设置Future结果值
//            future.complete(price);
//        }).start();
//        return future;

        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }
}
