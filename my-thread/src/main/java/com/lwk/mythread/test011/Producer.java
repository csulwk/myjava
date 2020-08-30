package com.lwk.mythread.test011;

import java.util.concurrent.locks.Lock;

/**
 * @author kai
 * @date 2020-08-29 16:36
 */
public class Producer extends Thread{
    private final Lock lock;
    private final Shared shared;
    Producer(Shared shared) {
        this.shared = shared;
        lock = shared.getLock();
    }

    @Override
    public void run() {
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            lock.lock();
            try {
                System.out.println("producer run...");
                shared.setShardChar(ch);
                System.out.println(ch + " produced by producer.");
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
