package com.lwk.agent.t1;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author kai
 * @date 2023-04-15 16:36
 */
@Slf4j
public class MyInterceptor {
    @RuntimeType
    public static Object intercept(
            // 方法入参
              @AllArguments Object[] args
            // 当前拦截的目标方法
            , @Origin Method method
            , @SuperCall Callable<?> callable ) throws Exception {
        log.info("MyInterceptor.intercept: method = {}, args = {}", method.getName(), args);
        long start = System.currentTimeMillis();
        try {
            Object result = callable.call();
            log.info("MyInterceptor.intercept: method = {}, result = {}", method.getName(), result);
            return result;
        } catch (Exception e) {
            log.error("方法执行发生异常" + e.getMessage());
            throw e;
        } finally {
            log.info("MyInterceptor.intercept: method = {}, elapsedTime = {} ms.", method, (System.currentTimeMillis() - start));
        }
    }
}
