package com.springboot.javaee.chapter9.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/11/2510:16 AM
 */
@Component
public class Receiver {
    //@JmsListener注解接收监听消息
    @JmsListener(destination = "my-dest")
    public void receiveMessage(String message) {
        System.out.println("接收的消息：<" + message + ">");
    }
}
