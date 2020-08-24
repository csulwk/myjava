package com.lwk.mythread.test006;

import java.util.Arrays;

/**
 * 同步屏障
 * @author kai
 * @date 2020-08-20 22:07
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        float[][] matrix = new float[3][3];
        int counter = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] = counter++;
            }
        }
        System.out.println("初始化矩阵...");
        dump(matrix);
        new Solver(matrix);
        System.out.println("并行处理后...");
        dump(matrix);
        System.out.println("====END====");
    }

    /**
     * 打印二维数组
     * @param matrix 二维数组
     */
    private static void dump(float[][] matrix) {
        for (float[] val : matrix) {
            System.out.println(Arrays.toString(val));
        }
    }
}

