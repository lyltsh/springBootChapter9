package com.springboot.javaee.chapter9.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/11/2510:13 AM
 */
@SpringBootApplication
public class Ch934Application implements CommandLineRunner {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Ch934Application.class, args);
    }

    /**
     * 程序启动后执行的代码
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        jmsTemplate.send("my-dest", new Msg());
    }
}
