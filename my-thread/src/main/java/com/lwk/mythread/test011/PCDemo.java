package com.lwk.mythread.test011;

/**
 * 参考： com.lwk.mythread.test001
 * @author kai
 * @date 2020-08-29 16:24
 */
public class PCDemo {
    public static void main(String[] args) {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }
}
