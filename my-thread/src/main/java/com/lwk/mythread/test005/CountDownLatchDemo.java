package com.lwk.mythread.test005;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时门闩
 * @author kai
 * @date 2020-08-19 23:01
 */
public class CountDownLatchDemo {

    public static final int THREAD_NUM = 3;

    public static void main(String[] args) {
        CountDownLatch sLatch = new CountDownLatch(1);
        CountDownLatch eLatch = new CountDownLatch(THREAD_NUM);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Runnable runnable = () -> {
            try {
                System.out.println(df.format(new Date()) + " : " + Thread.currentThread() + " : run...");
                sLatch.await();
                System.out.println(df.format(new Date()) + " : " + Thread.currentThread() + " : work...");
                Thread.sleep((int) (Math.random() * 1000));
                eLatch.countDown();
            } catch (InterruptedException e) {
                System.out.println("Runnable exception..." + e);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            executor.execute(runnable);
        }

        try {
            System.out.println("main thread run...1");
            Thread.sleep(1000);
            sLatch.countDown();
            System.out.println("main thread run...2");
            eLatch.await();
            executor.shutdownNow();
        } catch (InterruptedException e) {
            System.out.println("Main exception..." + e);
        }
        System.out.println("====END====");
    }
}
