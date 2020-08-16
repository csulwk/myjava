package com.lwk.mythread.demo;

/**
 * 生产者
 * @author kai
 * @date 2020-08-16 10:03
 */
public class Producer extends Thread{

    private final Shared s;

    public Producer(Shared s) {
        this.s = s;
    }

    @Override
    public void run() {
        for (char ch = 'A'; ch <= 'Z'; ch++){
            synchronized (s) {
                s.setSharedChar(ch);
                System.out.println("Produced++++: " + ch);
            }
        }
    }
}
