package com.lwk.mythread.test010;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kai
 * @date 2020-08-29 15:22
 */
public class RLockDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        final ReentrantLock lock = new ReentrantLock();

        class Worker implements Runnable {
            private final String name;

            private Worker(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                System.out.printf("THread %s begein to run...%n", name);
                lock.lock();
                try {
                    if (lock.isHeldByCurrentThread()) {
                        System.out.printf("THread %s entered critial section...%n", name);
                    }
                    System.out.printf("Thread %s performing work...%n", name);
                    Thread.sleep(2000);
                    System.out.printf("Thread %s finished work...%n", name);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted..." + name);
                } finally {
                    lock.unlock();
                }
            }
        }

        executor.execute(new Worker("Th-A"));
        executor.execute(new Worker("Th-B"));
        executor.execute(new Worker("Th-C"));
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main interrupted...");
        }
        executor.shutdownNow();
    }
}
