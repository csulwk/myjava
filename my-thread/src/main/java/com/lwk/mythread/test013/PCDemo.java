package com.lwk.mythread.test013;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author kai
 * @date 2020-08-31 22:50
 */
public class PCDemo {
    public static void main(String[] args) {
        final BlockingQueue<Character> queue = new ArrayBlockingQueue<>(26);
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable producer = () -> {
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                try {
                    queue.put(ch);
                    System.out.printf("%c produced by producer.%n", ch);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        executor.execute(producer);

        Runnable consumer = () -> {
            char ch = '\0';
            do {
                try {
                    ch = queue.take();
                    System.out.printf("%c consumed by consumer.%n", ch);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(ch != 'Z');
            executor.shutdownNow();
        };
        executor.execute(consumer);
    }
}
