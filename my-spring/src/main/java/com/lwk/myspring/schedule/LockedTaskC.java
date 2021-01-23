package com.lwk.myspring.schedule;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * https://github.com/lukas-krecan/ShedLock
 *
 * @author kai
 * @date 2021-01-23 11:05
 */
//@Component
@Slf4j
public class LockedTaskC {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String CORN_EXP_C = "0/5 * * * * ?";
    private static final String LOCK_TASK_NAME_C = "TC";
    private static final String LOCK_AT_LEAST = "10s";
    private static final String LOCK_AT_MOST = "15s";
    private static final long WAIT_TIME = 20*1000;

    @Scheduled(cron = CORN_EXP_C)
    @SchedulerLock(name = LOCK_TASK_NAME_C, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskC1() throws InterruptedException {
        log.info("TaskC3 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskC3 time： {}" , SDF.format(new Date()));
    }

    @Scheduled(cron = CORN_EXP_C)
    @SchedulerLock(name = LOCK_TASK_NAME_C, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskC2() throws InterruptedException {
        log.info("TaskC3 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskC3 time： {}" , SDF.format(new Date()));
    }

    @Scheduled(cron = CORN_EXP_C)
    @SchedulerLock(name = LOCK_TASK_NAME_C, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskC3() throws InterruptedException {
        log.info("TaskC3 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskC3 time： {}" , SDF.format(new Date()));
    }
}
