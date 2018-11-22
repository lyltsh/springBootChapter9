package com.springboot.javaee.chapter9.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: leiyulin
 * @description: 定时任务，每次发送不同的数据
 * @date:2018/9/2911:12 AM
 */
public class ScheduleDemo {
    private static final int TIME_INTERVAL = 40;

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public static void sendData(XFAudioSendTask sendTask) {
        System.out.println("schedule, sendTask.data:" + sendTask.getData());
        executorService.schedule(sendTask, TIME_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        SendDemo sendDemo = new SendDemo();
        XFAudioSendTask sendTask = new XFAudioSendTask(sendDemo, "");
        sendData(sendTask);
        for (int i = 0; i < 10; i++) {
            String data = "send data: " + i;
            System.out.println(data);
            sendTask.setData(data);
            System.out.println("sendTask.data:" + sendTask.getData());

        }
    }

    private static class SendDemo {
        public void send(String data) {
            System.out.println(Thread.currentThread().getName() + ", " + data);
        }
    }

    private static class XFAudioSendTask implements Runnable {

        private SendDemo sendDemo;
        private String data;

        public XFAudioSendTask(SendDemo sendDemo, String data) {
            this.sendDemo = sendDemo;
            this.data = data;
        }

        @Override
        public void run() {
            if (data != null) {
                sendDemo.send(data);
            }
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
