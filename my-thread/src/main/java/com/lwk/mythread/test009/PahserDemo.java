package com.lwk.mythread.test009;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * phaser屏障
 * @author kai
 * @date 2020-08-26 23:21
 */
public class PahserDemo {
    public static void main(String[] args) {
        System.out.println("====START====");
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(() -> System.out.printf("%s running at %d%n", Thread.currentThread().getName(), System.currentTimeMillis()));
        tasks.add(() -> System.out.printf("%s running at %d%n", Thread.currentThread().getName(), System.currentTimeMillis()));
        tasks.add(() -> System.out.printf("%s running at %d%n", Thread.currentThread().getName(), System.currentTimeMillis()));
        tasks.add(() -> System.out.printf("%s running at %d%n", Thread.currentThread().getName(), System.currentTimeMillis()));

        runTasks(tasks);
    }

    private static void runTasks(List<Runnable> tasks) {
        final Phaser phaser = new Phaser(1);
        for(final Runnable task : tasks) {
            // 添加一条尚未抵达的线程
            System.out.printf("Register: %d%n", phaser.register());
            Runnable r = () -> {
                try {
                    Thread.sleep(50 + (int)(Math.random() * 300));
                } catch (InterruptedException e) {
                    System.out.println("interrupted thread...");
                }
                // 记录到达并等待phaser前进
                System.out.printf("ArriveAndAwait: %d%n", phaser.arriveAndAwaitAdvance());
                task.run();
            };
            Executors.newSingleThreadExecutor().execute(r);
        }
        // 抵达此phaser，同时从中注销而不会等待其他线程到达
        phaser.arriveAndDeregister();
    }
}
