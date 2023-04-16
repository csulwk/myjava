package com.lwk.agent.t2;

/**
 * @author kai
 * @date 2023-04-15 21:41
 */
public interface MyCallable {
    /**
     * 定义一个方法, 方法名随意 {此类中只能有这一个方法, 只能有一个入参}
     *
     * @param args  方法入参
     * @return
     */
    Object call(Object[] args);
}
