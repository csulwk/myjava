package com.lwk.mythread.demo;

/**
 * 共享对象
 * @author kai
 * @date 2020-08-16 10:02
 */
class Shared {
    private char c;
    private volatile boolean writeable = true;

    synchronized void setSharedChar(char c) {
        while (!writeable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.c = c;
        writeable = false;
        notify();
    }

    synchronized char getSharedChar() {
        while (writeable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeable = true;
        notify();
        return c;
    }
}
