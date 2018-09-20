package com.springboot.javaee.chapter9.checkmd5;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/9/20上午11:10
 */
public class Checkmd5 {

    public static void main(String[] args) {
        String md5 = "ab527094b724438b37e06e94b57602c1";
        System.out.println(md5.hashCode());
        System.out.println((md5.hashCode() % 10 + 10) % 10);
    }
}
