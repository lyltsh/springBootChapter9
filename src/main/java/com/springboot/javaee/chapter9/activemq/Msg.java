package com.springboot.javaee.chapter9.activemq;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/11/2510:10 AM
 */
public class Msg implements MessageCreator {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS sss");

    @Override
    public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage("测试消息" + sdf.format(new Date()));
    }
}
