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

package org.apache.shardingsphere.agent.plugin.tracing.core.advice.adviser.impl;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.shardingsphere.agent.advisor.ClassAdvisor;
import org.apache.shardingsphere.agent.advisor.InstanceMethodAdvisor;
import org.apache.shardingsphere.agent.core.advisor.ClassAdvisorRegistryFactory;
import org.apache.shardingsphere.agent.core.plugin.advice.InstanceMethodAroundAdvice;
import org.apache.shardingsphere.agent.plugin.tracing.core.advice.adviser.TracingAdviser;

/**
 * SQL parser engine adviser.
 */
@RequiredArgsConstructor
public final class SQLParserEngineAdviser implements TracingAdviser {
    
    private static final String TARGET_CLASS = "org.apache.shardingsphere.infra.parser.ShardingSphereSQLParserEngine";
    
    private static final String TARGET_METHOD = "parse";
    
    private final String type;
    
    @Override
    public ClassAdvisor getAdvisor(final Class<? extends InstanceMethodAroundAdvice> sqlParserEngineAdvice) {
        ClassAdvisor result = ClassAdvisorRegistryFactory.getRegistry(type).getAdvisor(TARGET_CLASS);
        result.getInstanceMethodAdvisors().add(new InstanceMethodAdvisor(ElementMatchers.named(TARGET_METHOD), sqlParserEngineAdvice.getName()));
        return result;
    }
}
