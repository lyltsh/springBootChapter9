package com.springboot.javaee.chapter9.waitfinish;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@Slf4j
public class ConcurrentTask {

    private CountDownLatch latch;

    private List<Future> results;


    public ConcurrentTask(List<Callable> tasks) {
        this(tasks.toArray(new Callable[0]));
    }

    public ConcurrentTask(Callable... tasks) {
        latch = new CountDownLatch(tasks.length);
        results = new ArrayList<>(tasks.length);

        for (Callable task : tasks) {
            Future future = ThreadUtil.THIRDLIB_POOL.submit(() -> {
                Object res = null;
                try {
                    res = task.call();
                } catch (Exception e) {
                    log.error("cmd", "task_call_error", e);
                    return null;

                } finally {
                    latch.countDown();
                }
                return res;
            });
            results.add(future);
        }
    }

    public List<Future> waitFinish() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return results;
    }
}
