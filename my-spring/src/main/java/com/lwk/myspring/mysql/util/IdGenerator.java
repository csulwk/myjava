package com.lwk.myspring.mysql.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 与snowflake算法区别,返回字符串id,占用更多字节,但直观从id中看出生成时间
 *
 * @author kai
 * @date 2020-12-17 22:41
 */
public class IdGenerator {
    /** 用ip地址最后几个字节标示 */
    private long workerId;
    /** 配置在properties中,启动时加载,此处默认先写成0 */
    private long datacenterId = 0L;
    /** sequence */
    private long sequence = 0L;
    /** 节点ID长度 */
    private long workerIdBits = 8L;
    /** 数据中心ID长度,可根据时间情况设定位数 */
    private long datacenterIdBits = 2L;
    /** 序列号12位 */
    private long sequenceBits = 12L;
    /** 机器节点左移12位 */
    private long workerIdShift = sequenceBits;
    /** 数据中心节点左移14位 */
    private long datacenterIdShift = sequenceBits + workerIdBits;
    /** 4095 */
    private long sequenceMask = ~(-1L << sequenceBits);
    /** lastTimestamp */
    private long lastTimestamp = -1L;

    public IdGenerator() {
        workerId = 0x000000FF & getLastIp();
    }
    private synchronized String nextId() {
        long timestamp = getTimestamp();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("服务器时钟被调整了,ID生成器停止服务 %d ms", lastTimestamp - timestamp));
        }
        // 如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                // 自旋等待到下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
            sequence = 0L;
        }
        lastTimestamp = timestamp;


        long suffix = (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;

        String datePrefix = DateFormatUtils.format(timestamp, "yyyyMMddHHMMssSSS");

        return datePrefix + suffix;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = getTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getTimestamp();
        }
        return timestamp;
    }

    private long getTimestamp() {
        return System.currentTimeMillis();
    }

    private byte getLastIp() {
        byte lastIp = 0;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            byte[] ipByte = ip.getAddress();
            lastIp = ipByte[ipByte.length - 1];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return lastIp;
    }

    public static void main(String[] args) throws UnknownHostException {
        IdGenerator idGenerator = new IdGenerator();
        for (int i = 0; i < 100; i++) {
            System.out.println(idGenerator.nextId());
        }
        InetAddress addr = InetAddress.getLocalHost();
        //获取本机ip
        String ip=addr.getHostAddress().toString();
        //获取本机计算机名称
        String hostName=addr.getHostName().toString();
        System.out.println(ip + ";" + hostName);
        System.out.println(ip.hashCode() + ";" + hostName.hashCode());
        System.out.println((ip + hostName).hashCode());
    }
}

