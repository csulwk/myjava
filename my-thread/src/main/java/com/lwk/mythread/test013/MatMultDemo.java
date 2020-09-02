package com.lwk.mythread.test013;

import Jama.Matrix;

/**
 * @author kai
 * @date 2020-08-31 23:54
 */
public class MatMultDemo {
    public static void main(String[] args) {
        Matrix ma = new Matrix(1, 3);
        ma.set(0, 0, 1);
        ma.set(0, 1, 2);
        ma.set(0, 2, 3);
        ma.print(4, 0);

        Matrix mb = new Matrix(3, 2);
        mb.set(0, 0, 4);
        mb.set(1, 0, 5);
        mb.set(2, 0, 6);
        mb.set(0, 1, 7);
        mb.set(1, 1, 8);
        mb.set(2, 1, 9);
        mb.print(4, 0);

        Matrix mc = ma.times(mb);
        mc.print(4, 0);
    }

    public static void dump(Matrix matrix) {
        System.out.println("========");
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            matrix.print(i, 0);
        }
    }
}
