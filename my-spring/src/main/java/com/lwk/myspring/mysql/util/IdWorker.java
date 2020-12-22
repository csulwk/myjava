package com.lwk.myspring.mysql.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * ID生成规则：
 * 20201217234131001
 * @author kai
 * @date 2020-12-17 23:23
 */
@Slf4j
public class IdWorker {

    /**
     * 17位，获取当前系统时间
     * @return 当前系统时间
     */
    private static String getSystemTime() {
        return new SimpleDateFormat("yyyyMMddHHMMssSSS").format(new Date());
    }

    /**
     * 获取当前系统信息
     * @return 当前系统信息
     */
    private static String getSystemInfo(int len) {
        StringBuilder sys = new StringBuilder();
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            //获取本机ip
            String ip=addr.getHostAddress().toString();
            sys.append(ip);
            //获取本机计算机名称
            String hostName=addr.getHostName().toString();
            sys.append(hostName);
        } catch (UnknownHostException e) {
            log.error("获取系统信息异常...", e);
        }
        int subVal = Math.abs(sys.toString().hashCode()) % 100;
        return StringUtils.leftPad(String.valueOf(subVal), len, "0");
    }
    /** 创建一个空实例对象，类需要用的时候才赋值 */
    private static IdWorker obj = null;
    public static synchronized IdWorker getInstance() {
        if (obj == null) {
            obj = new IdWorker();
        }
        return obj;
    }
    /** 全局自增数 */
    private static int count = 0;

    /** 每毫秒最多生成多少订单（最好是像9999这种准备进位的值）*/
    private static final int TOTAL = 9999;
    /** 记录上一次的时间，用来判断是否需要递增全局数 */
    private static String lastTime = null;
    /**
     * 生成订单号
     * @return 订单号
     */
    public synchronized String nextId() {
        String nowTime = getSystemTime();
        if (nowTime.equals(lastTime)) {
            count++;
        } else {
            count = 1;
            lastTime = nowTime;
        }
        if (count >= TOTAL) {
            count = 0;
        }
        return nowTime + getSystemInfo(2) + StringUtils.leftPad(String.valueOf(count), 2, "0");
    }

    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        int rp = 0;
        for (int i = 0; i < 2; i++) {
            String id = IdWorker.getInstance().nextId();
            System.out.println(id);
            if (set.contains(id)) {
                rp++;
            }
            set.add(id);
        }
        System.out.println("rp: " + rp);
    }
}
