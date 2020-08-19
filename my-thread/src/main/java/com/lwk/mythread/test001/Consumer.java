package com.lwk.mythread.test001;

/**
 * 消费者
 * @author kai
 * @date 2020-08-16 10:03
 */
public class Consumer extends Thread{

    private final Shared s;

    public Consumer(Shared s) {
        this.s = s;
    }

    @Override
    public void run() {
        char ch;
        do {
            synchronized (s) {
                ch = s.getSharedChar();
                System.out.println("Consumed----: " + ch);
            }
        } while (ch != 'Z');
    }
}
