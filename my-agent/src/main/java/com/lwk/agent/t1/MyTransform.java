package com.lwk.agent.t1;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;

import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * @author kai
 * @date 2023-04-15 19:14
 */
public class MyTransform implements AgentBuilder.Transformer {
    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, ProtectionDomain protectionDomain) {
        return builder
                // 拦截任意方法
                .method( named("sayHello")
                        .or(named("map"))
                        .or(named("processElement"))
                        .or(named("info"))
                )
                // 委托
                .intercept(MethodDelegation
                        .to(MyInterceptor.class)
                );
    }
}
