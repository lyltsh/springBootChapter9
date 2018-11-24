package com.springboot.javaee.chapter9.future;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/11/2311:20 AM
 */
public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    /**
     * 根据一个Quote返回一个折扣信息
     *
     * @param quote
     * @return
     */
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    /**
     * 根据价格和折扣计算折扣后的价格
     *
     * @param price
     * @param code
     * @return
     */
    private static double apply(double price, Code code) {
        //模拟Discount服务的响应延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (price * (100 - code.percentage) / 100);
    }
}
