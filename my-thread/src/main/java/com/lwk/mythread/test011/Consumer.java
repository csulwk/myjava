package com.lwk.mythread.test011;

import java.util.concurrent.locks.Lock;

/**
 * @author kai
 * @date 2020-08-29 16:41
 */
public class Consumer extends Thread {
    private final Lock lock;
    private final Shared shared;

    Consumer(Shared shared) {
        this.shared = shared;
        lock = shared.getLock();
    }

    @Override
    public void run() {
        char ch = 0;
        do {
            lock.lock();
            try {
                System.out.println("consumer run...");
                ch = shared.getSharedChar();
                System.out.println(ch + " consumed by consumer.");
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } while (ch != 'Z');
    }
}
