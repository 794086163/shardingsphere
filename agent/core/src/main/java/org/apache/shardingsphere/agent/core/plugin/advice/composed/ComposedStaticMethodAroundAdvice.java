/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.agent.core.plugin.advice.composed;

import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.agent.core.plugin.advice.StaticMethodAroundAdvice;
import org.apache.shardingsphere.agent.core.plugin.MethodInvocationResult;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Composed class static method around advice.
 */
@RequiredArgsConstructor
public final class ComposedStaticMethodAroundAdvice implements StaticMethodAroundAdvice {
    
    private final Collection<StaticMethodAroundAdvice> advices;
    
    @Override
    public void beforeMethod(final Class<?> clazz, final Method method, final Object[] args, final MethodInvocationResult result) {
        advices.forEach(each -> each.beforeMethod(clazz, method, args, result));
    }
    
    @Override
    public void afterMethod(final Class<?> clazz, final Method method, final Object[] args, final MethodInvocationResult result) {
        advices.forEach(each -> each.afterMethod(clazz, method, args, result));
    }
    
    @Override
    public void onThrowing(final Class<?> clazz, final Method method, final Object[] args, final Throwable throwable) {
        advices.forEach(each -> each.onThrowing(clazz, method, args, throwable));
    }
}
