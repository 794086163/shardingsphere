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
import org.apache.shardingsphere.agent.core.plugin.advice.ConstructorAdvice;

import java.util.Collection;

/**
 * Composed Constructor advice.
 */
@RequiredArgsConstructor
public final class ComposedConstructorAdvice implements ConstructorAdvice {
    
    private final Collection<ConstructorAdvice> advices;
    
    @Override
    public void onConstructor(final TargetAdviceObject target, final Object[] args) {
        advices.forEach(each -> each.onConstructor(target, args));
    }
}
