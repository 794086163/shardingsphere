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

package org.apache.shardingsphere.agent.core.plugin;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import org.apache.shardingsphere.agent.advisor.ClassAdvisor;
import org.apache.shardingsphere.agent.advisor.ConstructorAdvisor;
import org.apache.shardingsphere.agent.advisor.InstanceMethodAdvisor;
import org.apache.shardingsphere.agent.advisor.StaticMethodAdvisor;
import org.apache.shardingsphere.agent.core.mock.advice.MockConstructorAdvice;
import org.apache.shardingsphere.agent.core.mock.advice.MockInstanceMethodAroundAdvice;
import org.apache.shardingsphere.agent.core.mock.advice.MockStaticMethodAroundAdvice;
import org.apache.shardingsphere.agent.core.plugin.loader.AdviceInstanceLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.internal.configuration.plugins.Plugins;
import org.mockito.internal.util.reflection.FieldReader;
import org.mockito.plugins.MemberAccessor;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public final class AgentAdvisorsTest {
    
    private static final AgentAdvisors AGENT_ADVISORS = new AgentAdvisors(Collections.emptyList(), true);
    
    private static final TypePool POOL = TypePool.Default.ofSystemLoader();
    
    private static final TypeDescription FAKE = POOL.describe("java.lang.String").resolve();
    
    private static final TypeDescription MATERIAL = POOL.describe("org.apache.shardingsphere.agent.core.mock.material.Material").resolve();
    
    @BeforeClass
    @SuppressWarnings("unchecked")
    public static void setup() throws NoSuchFieldException, IllegalAccessException {
        FieldReader objectPoolReader = new FieldReader(AdviceInstanceLoader.class, AdviceInstanceLoader.class.getDeclaredField("ADVICE_INSTANCE_CACHE"));
        Map<String, Object> objectPool = (Map<String, Object>) objectPoolReader.read();
        objectPool.put(MockConstructorAdvice.class.getTypeName(), new MockConstructorAdvice());
        objectPool.put(MockInstanceMethodAroundAdvice.class.getTypeName(), new MockInstanceMethodAroundAdvice());
        objectPool.put(MockStaticMethodAroundAdvice.class.getTypeName(), new MockStaticMethodAroundAdvice());
        ClassAdvisor classAdvisor = new ClassAdvisor("org.apache.shardingsphere.agent.core.mock.material.Material");
        classAdvisor.getConstructorAdvisors().add(new ConstructorAdvisor(ElementMatchers.takesArguments(1), MockConstructorAdvice.class.getTypeName()));
        classAdvisor.getInstanceMethodAdvisors().add(new InstanceMethodAdvisor(ElementMatchers.named("mock"), MockInstanceMethodAroundAdvice.class.getTypeName()));
        classAdvisor.getStaticMethodAdvisors().add(new StaticMethodAdvisor(ElementMatchers.named("staticMock"), MockStaticMethodAroundAdvice.class.getTypeName()));
        MemberAccessor accessor = Plugins.getMemberAccessor();
        accessor.set(AGENT_ADVISORS.getClass().getDeclaredField("advisors"), AGENT_ADVISORS, Collections.singletonMap(classAdvisor.getTargetClassName(), classAdvisor));
    }
    
    @Test
    public void assertTypeMatcher() {
        assertTrue(AGENT_ADVISORS.createTypeMatcher().matches(MATERIAL));
        assertFalse(AGENT_ADVISORS.createTypeMatcher().matches(FAKE));
    }
    
    @Test
    public void assertContainsType() {
        assertTrue(AGENT_ADVISORS.containsType(MATERIAL));
        assertFalse(AGENT_ADVISORS.containsType(FAKE));
    }
    
    @Test
    public void assertGetPluginAdvisor() {
        assertNotNull(AGENT_ADVISORS.getPluginAdvisor(MATERIAL));
    }
}
