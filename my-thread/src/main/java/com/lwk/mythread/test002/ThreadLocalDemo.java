package com.lwk.mythread.test002;

/**
 * @author kai
 * @date 2020-08-17 22:36
 */
public class ThreadLocalDemo {
    private static volatile ThreadLocal<String> userID = new ThreadLocal<>();

    public static void main(String[] args) {
        Runnable r = () -> {
            String threadName = Thread.currentThread().getName();
            if ("A".equals(threadName)) {
                userID.set("userA");
            } else {
                userID.set("userB");
            }
            System.out.printf("Thread: %s-%s%n", threadName , userID.get());
        };
        Thread ta = new Thread(r);
        ta.setName("A");

        Thread tb = new Thread(r);
        tb.setName("B");

        ta.start();
        tb.start();

        // ThreadLocal字段应该至少调用一次remove()方法
        userID.remove();
    }
}
