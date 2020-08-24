package com.lwk.mythread.test007;

import java.util.concurrent.Exchanger;

/**
 * 交换器
 * @author kai
 * @date 2020-08-24 21:23
 */
public class ExchangeDemo {

    private final static Exchanger<DataBuffer> EXCHANGER = new Exchanger<>();
    private final static DataBuffer INITIAL_EMPTY_BUFFER = new DataBuffer();
    private final static DataBuffer INITIAL_FULL_BUFFER = new DataBuffer("I-");

    public static void main(String[] args) {

        class FillingLoop implements Runnable {
            private int count = 0;
            @Override
            public void run() {
                DataBuffer currentBuffer = INITIAL_EMPTY_BUFFER;
                try {
                    while (true) {
                        addToBuffer(currentBuffer);
                        if (currentBuffer.isFull()) {
                            System.out.println("filling thread wants to exchange...");
                            currentBuffer = EXCHANGER.exchange(currentBuffer);
                            System.out.println("filling thread receives exchange...");

                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("filling thread interrupted...");
                }
            }

            /**
             * 添加数据
             * @param buffer DataBuffer
             */
            private void addToBuffer(DataBuffer buffer) {
                String item = "NI-" + count++;
                System.out.println("Adding: " + item);
                buffer.add(item);
            }
        }

        class EmptyingLoop implements  Runnable {
            @Override
            public void run() {
                DataBuffer currentBuffer = INITIAL_FULL_BUFFER;
                try {
                    while (true) {
                        takeFromBuffer(currentBuffer);
                        if (currentBuffer.isEmpty()) {
                            System.out.println("emptying thread wants to exchange...");
                            currentBuffer = EXCHANGER.exchange(currentBuffer);
                            System.out.println("emptying thread received exchange...");
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("emptying thread interrupted...");
                }
            }

            /**
             * 移出数据
             * @param buffer DataBuffer
             */
            private void takeFromBuffer(DataBuffer buffer) {
                System.out.println("Taking: " + buffer.remove());
            }
        }

        new Thread(new EmptyingLoop()).start();
        new Thread(new FillingLoop()).start();

    }
}
