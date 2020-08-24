package com.lwk.mythread.test008;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 计数信号量
 * @author kai
 * @date 2020-08-24 23:21
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        final Pool pool = new Pool();
        Runnable r = () -> {
            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    String item = pool.getItem();
                    System.out.printf(" %s\tacquiring\t%s%n", name, item);
                    Thread.sleep(200 + (int) (Math.random() * 100));
                    System.out.printf(" %s\tputting  \t%s%n", name, item);
                    pool.putItem(item);
                } catch (InterruptedException e) {
                    System.out.printf("interrupted...%s%n", name);
                }
            }
        };
        ExecutorService[] executors = new ExecutorService[Pool.MAX_AVAILABLE + 1];
        for (int i = 0; i < executors.length; i++) {
            executors[i] = Executors.newSingleThreadExecutor();
            executors[i].execute(r);
        }
    }
}
