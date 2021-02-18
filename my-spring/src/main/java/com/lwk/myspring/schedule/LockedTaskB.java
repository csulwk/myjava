package com.lwk.myspring.schedule;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@Component
//@EnableScheduling
@EnableAsync
@Slf4j
public class LockedTaskB {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String CORN_EXP_B = "0/5 * * * * ?";
    private static final String LOCK_TASK_NAME_B = "TB";
    private static final String LOCK_AT_LEAST = "5s";
    private static final String LOCK_AT_MOST = "10s";
    private static final long WAIT_TIME = 15*1000;

    //@Async
    @Scheduled(cron = CORN_EXP_B)
    @SchedulerLock(name = LOCK_TASK_NAME_B, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskB1() throws InterruptedException {
        log.info("TaskB1 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskB1 time： {}" , SDF.format(new Date()));
    }

    //@Scheduled(cron = CORN_EXP_B)
    @SchedulerLock(name = LOCK_TASK_NAME_B, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskB2() throws InterruptedException {
        log.info("TaskB2 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskB2 time： {}" , SDF.format(new Date()));
    }

    //@Scheduled(cron = CORN_EXP_B)
    @SchedulerLock(name = LOCK_TASK_NAME_B, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskB3() throws InterruptedException {
        log.info("TaskB3 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskB3 time： {}" , SDF.format(new Date()));
    }

}
