package com.springboot.javaee.chapter9.stream;

import lombok.Data;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/10/101:09 PM
 */
@Data
public class Weight {
    private String color;
    private long weight;

    public Weight(String color, long weight) {
        this.color = color;
        this.weight = weight;
    }
}
