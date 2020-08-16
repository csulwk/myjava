package com.lwk.mythread.demo;

/**
 * 主函数
 * @author kai
 * @date 2020-08-16 10:02
 */
public class PcMain {

    public static void main(String[] args) {
        System.out.println("====BEN====");
        Shared s= new Shared();
        new Producer(s).start();
        new Consumer(s).start();
        System.out.println("====END====");
    }
}
