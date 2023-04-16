package com.lwk.agent;

import com.lwk.agent.t1.MyTransform;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

/**
 * @author kai
 * @date 2023-04-15 16:28
 */
@Slf4j
public class MyAgent {
    public static void premain(String args, Instrumentation inst) {
        log.info("this is my agent：args = {}", args);

        new AgentBuilder
                .Default()
                .ignore(nameStartsWith("java.").or(nameStartsWith("sun.")))
                .type(   nameStartsWith("com.lwk.demo")
                        .or(nameStartsWith("com.demo"))
                        //.or(nameStartsWith("com.flink.function.PersonMapFunction"))
                        .or(nameStartsWith("org.apache.flink.api.common.functions"))
                        .or(nameStartsWith("org.apache.flink.table.runtime"))
                        .or(nameStartsWith("org.apache.logging.slf4j.Log4jLogger"))
                        .or(nameStartsWith("org.slf4j"))
                )
                .transform(new MyTransform())
                .with(new MyListener())
                .installOn(inst);
    }
}
