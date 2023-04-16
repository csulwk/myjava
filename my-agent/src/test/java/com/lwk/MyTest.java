package com.lwk;

import com.lwk.agent.MyAgent;
import com.lwk.demo.Greeting;
import com.lwk.demo.MyGreeting;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.junit.Test;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * @author kai
 * @date 2023-04-15 22:25
 */
@Slf4j
public class MyTest {

    @Test
    public void test1() throws Exception {
        log.info("test1...");
        ByteBuddyAgent.install();
        Instrumentation inst = ByteBuddyAgent.getInstrumentation();
        // 增强
        MyAgent.premain(null, inst);
        // 调用
        Class greetingType = Greeting.class.getClassLoader().loadClass(Greeting.class.getName());
        Method sayHello = greetingType.getDeclaredMethod("sayHello", String.class);
        sayHello.invoke(null, "developer");
    }

    @Test
    public void test2() throws Exception {
        log.info("test2...");
        Greeting greeting = new ByteBuddy()
                .subclass(Greeting.class)
                .method(named("sayGreet").and(returns(String.class)))
                .intercept(to(MyGreeting.class))
                .make()
                .load(MyGreeting.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
        String result = greeting.sayGreet("abc");
        log.info("result = {}", result);
    }
}
