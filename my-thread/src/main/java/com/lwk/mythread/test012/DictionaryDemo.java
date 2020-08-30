package com.lwk.mythread.test012;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 重入读写锁
 * @author kai
 * @date 2020-08-30 10:41
 */
public class DictionaryDemo {
    public static void main(String[] args) {
        final String[] words = {
                "AAA",
                "BBB",
                "CCC",
                "DDD",
                "EEE",
                "FFF"
        };

        final String[] definitions = {
                "Hello world",
                "Good good study",
                "Day day up",
                "Long time no see",
                "Lose face",
                "No can do"
        };

        final Map<String, String> dictionary = new HashMap<>();
        // 使用公平的顺序策略
        ReadWriteLock lock = new ReentrantReadWriteLock(true);
        final Lock rl = lock.readLock();
        final Lock wl = lock.writeLock();

        Runnable writer = () -> {
            for (int i = 0; i < words.length; i++) {
                wl.lock();
                try {
                    dictionary.put(words[i], definitions[i]);
                    System.out.printf("writer storing \"%s\" entry.%n", words[i]);
                } finally {
                    wl.unlock();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("writer interrupted...");
                }
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(writer);

        Runnable reader = () -> {
            while (true) {
                rl.lock();
                try {
                    int i = (int) (Math.random() * words.length);
                    System.out.printf("reader %d accessing \"%s\" : \"%s\" entry.%n", i, words[i], dictionary.get(words[i]));
                } finally {
                  rl.unlock();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("reader interrupted...");
                }
            }
        };
        es = Executors.newFixedThreadPool(1);
        es.submit(reader);
    }
}
