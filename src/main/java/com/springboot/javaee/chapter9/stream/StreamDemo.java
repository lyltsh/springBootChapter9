package com.springboot.javaee.chapter9.stream;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/10/1011:43 AM
 */
public class StreamDemo {
    private static HashMap<String, Integer> hashMap = new HashMap<>();
    private static ArrayList<Weight> weights = new ArrayList<>();

    static {
        {
            hashMap.put("aaaa", 11111);
            hashMap.put("bbbb", 22222);
            hashMap.put("cccc", 33333);
            hashMap.put("dddd", 44444);
            hashMap.put("ffff", 55555);
        }
        {
            weights.add(new Weight("red", 100));
            weights.add(new Weight("red", 200));
            weights.add(new Weight("black", 150));
            weights.add(new Weight("black", 120));
        }
    }

    public static void useFilter() {
        hashMap.keySet().stream().filter(k -> k.startsWith("a")).forEach(System.out::println);
        System.out.println(weights.stream().filter((weight -> weight.getColor().equals("red"))).mapToLong(weight -> weight.getWeight()*2).sum());
    }

    public static void main(String[] args) {
        useFilter();
    }
}
