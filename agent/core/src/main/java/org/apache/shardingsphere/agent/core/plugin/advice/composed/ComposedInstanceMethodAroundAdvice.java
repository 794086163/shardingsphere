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
import org.apache.shardingsphere.agent.core.plugin.TargetAdviceObject;
import org.apache.shardingsphere.agent.core.plugin.advice.InstanceMethodAroundAdvice;
import org.apache.shardingsphere.agent.core.plugin.MethodInvocationResult;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Composed instance method around advice.
 */
@RequiredArgsConstructor
public final class ComposedInstanceMethodAroundAdvice implements InstanceMethodAroundAdvice {
    
    private final Collection<InstanceMethodAroundAdvice> advices;
    
    @Override
    public void beforeMethod(final TargetAdviceObject target, final Method method, final Object[] args, final MethodInvocationResult result) {
        advices.forEach(each -> each.beforeMethod(target, method, args, result));
    }
    
    @Override
    public void afterMethod(final TargetAdviceObject target, final Method method, final Object[] args, final MethodInvocationResult result) {
        advices.forEach(each -> each.afterMethod(target, method, args, result));
    }
    
    @Override
    public void onThrowing(final TargetAdviceObject target, final Method method, final Object[] args, final Throwable throwable) {
        advices.forEach(each -> each.onThrowing(target, method, args, throwable));
    }
}
