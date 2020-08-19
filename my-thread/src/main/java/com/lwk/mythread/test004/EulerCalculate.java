package com.lwk.mythread.test004;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @author kai
 * @date 2020-08-19 22:18
 */
public class EulerCalculate {

    private static final int LAST_ITER = 100;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Callable<BigDecimal> callable = new Callable<BigDecimal>() {

            /**
             * e = 1/0! + 1/1! + 1/2! + ... + 1/n!
             * 其数值约为：2.71828 18284 59045 23536 02874 71352 66249 77572 47093 69995 95749 66967 62772 40766 30353 54759 45713 82178 52516 64274
             * @return 计算结果
             * @throws Exception 异常
             */
            @Override
            public BigDecimal call() throws Exception {
                MathContext mc = new MathContext(100, RoundingMode.HALF_UP);
                BigDecimal result = BigDecimal.ZERO;
                for (int i = 0; i <= LAST_ITER; i++) {
                    BigDecimal factorial = factorial(new BigDecimal(i));
                    BigDecimal res = BigDecimal.ONE.divide(factorial, mc);
                    result = result.add(res);
                }
                return result;
            }

            /**
             * 阶乘
             * @param n BigDecimal
             * @return BigDecimal
             */
            private BigDecimal factorial(BigDecimal n) {
                if (BigDecimal.ZERO.equals(n)) {
                    return BigDecimal.ONE;
                } else {
                    return n.multiply(factorial(n.subtract(BigDecimal.ONE)));
                }
            }
        };

        Future<BigDecimal> taskFuture = executor.submit(callable);


        try {
            while (!taskFuture.isDone()) {
                System.out.println(df.format(new Date()));
            }
            System.out.println("result: " + taskFuture.get());
        } catch (InterruptedException e) {
            System.out.println("task interrupted..." + e);
        } catch (ExecutionException e) {
            System.out.println("task exception..." + e);
        }
        executor.shutdownNow();
    }
}
