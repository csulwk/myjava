package com.lwk.mythread.test011;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kai
 * @date 2020-08-29 16:26
 */
class Shared {
    private char ch;
    private volatile boolean avai;
    private final Lock lock;
    private final Condition cond;

    Shared() {
        avai = false;
        lock = new ReentrantLock();
        cond = lock.newCondition();
    }

    Lock getLock() {
        return lock;
    }

    char getSharedChar() {
        lock.lock();
        try {
            while(!avai) {
                try {
                    System.out.println("consumer await..." + ch);
                    // 接收到信号前线程处于等待中
                    cond.await();
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted...");
                }
            }
            avai = false;
            // 唤醒等待中的线程
            cond.signal();
        } finally {
            lock.unlock();
        }
        return ch;
    }

    void setShardChar(char ch) {
        lock.lock();
        try {
            while(avai) {
                try {
                    System.out.println("producer await..." + ch);
                    // 接收到信号前线程处于等待中
                    cond.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.ch = ch;
            avai = true;
            // 唤醒等待中的线程
            cond.signal();
        } finally {
            lock.unlock();
        }
    }
}
