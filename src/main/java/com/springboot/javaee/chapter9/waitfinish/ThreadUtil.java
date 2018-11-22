package com.springboot.javaee.chapter9.waitfinish;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadUtil {

//	public static final int POOL_SIZE = 1000;
	public static final int KS3_POOL_SIZE = 300;
	public static final int THIRDLIB_POOL_SIZE = 300;
	public static final int KAFKA_POOL_SIZE = 300;
	public static final int XF_SCHEDULED_SIZE = 5;

	
//	public static final ExecutorService POOL = Executors.newFixedThreadPool(POOL_SIZE);
	// ks3 相关操作的线程池
	public static final ExecutorService KS3_POOL = Executors.newFixedThreadPool(KS3_POOL_SIZE);
	// 第三方业务操作的线程池
	public static final ExecutorService THIRDLIB_POOL = Executors.newFixedThreadPool(THIRDLIB_POOL_SIZE);
	// kafka业务操作的线程池
	public static final ExecutorService KAFKA_POOL = Executors.newFixedThreadPool(KAFKA_POOL_SIZE);
	// 科大讯飞 定时间隔上送音频文件线程池
	public static final ScheduledExecutorService XF_SCHEDULED_POOL = Executors.newScheduledThreadPool(XF_SCHEDULED_SIZE);

}
