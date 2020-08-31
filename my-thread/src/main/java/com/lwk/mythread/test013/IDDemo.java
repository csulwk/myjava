package com.lwk.mythread.test013;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kai
 * @date 2020-08-31 23:31
 */
public class IDDemo {

    private static final int THRESHOLD = 20;

    public static void main(String[] args) {
        System.out.println("========");
        for (int i = 0; i < THRESHOLD; i++) {
            System.out.println("NUM: " + IDVolatile.getNextID());
        }

        System.out.println("========");
        for (int i = 0; i < THRESHOLD; i++) {
            System.out.println("NUM: " + IDAtomic.getNextID());
        }

    }
}

class IDVolatile {
    private static volatile long nextID = 1;
    static synchronized long getNextID() {
        return nextID++;
    }
}

class IDAtomic {
    private static AtomicLong nextID = new AtomicLong(1);
    static long getNextID() {
        return nextID.getAndIncrement();
    }
}

