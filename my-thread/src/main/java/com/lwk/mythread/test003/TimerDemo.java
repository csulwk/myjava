package com.lwk.mythread.test003;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author kai
 * @date 2020-08-18 21:15
 */
public class TimerDemo {

    public static void main(String[] args) {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("闹钟响了..." + Thread.currentThread().getName());
                System.out.println(dateFormat.format(new Date()));
            }
        };

        //  经过2000ms的初始延迟后执行一次
//        System.out.println(dateFormat.format(new Date()));
//        Timer timer = new Timer();
//        timer.schedule(task, 2000);

        ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(3,
                new BasicThreadFactory.Builder().namingPattern("exp-sch-%d").daemon(false).build());
        schedule.schedule(task, 2, TimeUnit.SECONDS);
        schedule.scheduleAtFixedRate(task, 2, 2, TimeUnit.SECONDS);
        schedule.scheduleWithFixedDelay(task, 3, 2, TimeUnit.SECONDS);

        System.out.println("====END====");
    }
}
