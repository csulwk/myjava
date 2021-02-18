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
public class LockedTaskA {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String CORN_EXP_A = "0/5 * * * * ?";
    private static final String LOCK_TASK_NAME_A = "TA";
    private static final String LOCK_AT_LEAST = "25s";
    private static final String LOCK_AT_MOST = "210s";
    private static final long WAIT_TIME = 15*1000;

    /**
     * SchedulerLock注解一共支持五个参数，分别是
     * name：用来标注一个定时服务的名字，被用于写入数据库作为区分不同服务的标识，如果有多个同名定时任务则同一时间点只有一个执行成功
     * lockAtMostFor：成功执行任务的节点所能拥有独占锁的最长时间，单位是毫秒ms
     * lockAtMostForString：成功执行任务的节点所能拥有的独占锁的最长时间的字符串表达，例如“PT14M”表示为14分钟
     * lockAtLeastFor：成功执行任务的节点所能拥有独占所的最短时间，单位是毫秒ms；保证在设置的期间类不执行多次任务，
     *                 此处可以根据实际任务运行情况进行设置，简单来说，一个每10分钟执行的任务，若每次任务执行的时间为几分钟，
     *                 则可以设置lockAtLeastFor大于其最大估计最大执行时间，避免一次任务未执行完，下一个定时任务又启动了。
     *                 任务执行完，会自动释放锁。
     * lockAtLeastForString：成功执行任务的节点所能拥有的独占锁的最短时间的字符串表达，例如“PT14M”表示为14分钟
     */
    //@Async
    @Scheduled(cron = CORN_EXP_A)
    @SchedulerLock(name = LOCK_TASK_NAME_A, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskA1() throws InterruptedException {
        log.info("TaskA1 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskA1 time： {}" , SDF.format(new Date()));
    }

    //@Scheduled(cron = CORN_EXP_A)
    @SchedulerLock(name = LOCK_TASK_NAME_A, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskA2() throws InterruptedException {
        log.info("TaskA2 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskA2 time： {}" , SDF.format(new Date()));
    }

    //@Scheduled(cron = CORN_EXP_A)
    @SchedulerLock(name = LOCK_TASK_NAME_A, lockAtMostFor = LOCK_AT_MOST, lockAtLeastFor = LOCK_AT_LEAST)
    public void taskA3() throws InterruptedException {
        log.info("TaskA3 ...");
        Thread.sleep(WAIT_TIME);
        log.info("TaskA3 time： {}" , SDF.format(new Date()));
    }

}
