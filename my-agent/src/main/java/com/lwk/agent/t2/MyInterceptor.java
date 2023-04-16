package com.lwk.agent.t2;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

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
            // 代理对象
            , @Morph MyCallable myCallable) throws Exception {
        log.info("MyInterceptor.intercept: method = {}, args = {}", method.getName(), args);
        long start = System.currentTimeMillis();
        try {
            args[0] = "222";
            Object result = myCallable.call(args);
            log.info("MyInterceptor.intercept: method = {}, result = {}", method.getName(), result);
            return result;
        } catch (Exception e) {
            log.error("方法执行发生异常" + e.getMessage());
            throw e;
        } finally {
            log.info("MyInterceptor.intercept: method = {}, elapsedTime = {} ms.", method.getName(), (System.currentTimeMillis() - start));
        }
    }
}
