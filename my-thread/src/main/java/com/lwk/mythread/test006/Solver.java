package com.lwk.mythread.test006;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author kai
 * @date 2020-08-24 22:50
 */
class Solver {
    private final int N;
    private final float[][] data;
    private final CyclicBarrier barrier;

    Solver(float[][] matrix) {
        N = matrix.length;
        data = matrix;
        // 同步屏障
        barrier = new CyclicBarrier(N, this::mergeRows);
        for (int i = 0; i < N; i++) {
            new Thread(new Worker(i)).start();
        }
        waitUntilDone();
    }

    /**
     * 唤醒等待中的线程
     */
    private void mergeRows() {
        System.out.println("Main merging...");
        synchronized ("abc") {
            "abc".notify();
        }
    }

    /**
     * 工作线程
     */
    class Worker implements Runnable {
        int myRow;
        boolean done = false;

        Worker(int row) {
            myRow = row;
        }

        @Override
        public void run() {
            while (!done) {
                processRow(myRow);
                try {
                    barrier.await();
                    System.out.println("Worker wait..." + myRow);
                } catch (InterruptedException e) {
                    System.out.println("Worker interrupted...");
                    return;
                } catch (BrokenBarrierException e) {
                    System.out.println("Worker exception...");
                    return;
                }
            }
        }

        /**
         * 调整当前行的数值
         * @param myRow 行数
         */
        void processRow(int myRow) {
            System.out.println("Worker..." + myRow);
            for (int i = 0; i < N; i++) {
                data[myRow][i] *= 10;
            }
            done = true;
        }
    }

    private void waitUntilDone() {
        synchronized ("abc") {
            try {
                System.out.println("Main waiting...");
                "abc".wait();
                System.out.println("Main notified...");
            } catch (InterruptedException e) {
                System.out.println("Main interrupted...");
            }
        }
    }
}
