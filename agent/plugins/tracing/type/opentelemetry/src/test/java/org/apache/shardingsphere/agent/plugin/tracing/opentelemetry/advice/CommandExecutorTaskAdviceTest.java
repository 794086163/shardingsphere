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

package org.apache.shardingsphere.agent.plugin.tracing.opentelemetry.advice;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import org.apache.shardingsphere.agent.core.plugin.MethodInvocationResult;
import org.apache.shardingsphere.agent.plugin.tracing.advice.AbstractCommandExecutorTaskAdviceTest;
import org.apache.shardingsphere.agent.plugin.tracing.opentelemetry.collector.OpenTelemetryCollector;
import org.apache.shardingsphere.agent.plugin.tracing.opentelemetry.constant.OpenTelemetryConstants;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public final class CommandExecutorTaskAdviceTest extends AbstractCommandExecutorTaskAdviceTest {
    
    @ClassRule
    public static final OpenTelemetryCollector COLLECTOR = new OpenTelemetryCollector();
    
    private CommandExecutorTaskAdvice advice;
    
    @Before
    public void setup() {
        advice = new CommandExecutorTaskAdvice();
    }
    
    @Test
    public void assertMethod() {
        advice.beforeMethod(getTargetObject(), null, new Object[]{}, new MethodInvocationResult());
        advice.afterMethod(getTargetObject(), null, new Object[]{}, new MethodInvocationResult());
        List<SpanData> spanItems = COLLECTOR.getSpanItems();
        assertThat(spanItems.size(), is(1));
        SpanData spanData = spanItems.get(0);
        assertThat(spanData.getName(), is("/ShardingSphere/rootInvoke/"));
        assertThat(spanData.getAttributes().get(AttributeKey.stringKey(OpenTelemetryConstants.COMPONENT)), is(OpenTelemetryConstants.COMPONENT_NAME));
        assertThat(spanData.getKind(), is(SpanKind.CLIENT));
    }
    
    @Test
    public void assertExceptionHandle() {
        advice.beforeMethod(getTargetObject(), null, new Object[]{}, new MethodInvocationResult());
        advice.onThrowing(getTargetObject(), null, new Object[]{}, new IOException());
        advice.afterMethod(getTargetObject(), null, new Object[]{}, new MethodInvocationResult());
        List<SpanData> spanItems = COLLECTOR.getSpanItems();
        assertThat(spanItems.size(), is(1));
        SpanData spanData = spanItems.get(0);
        assertThat(spanData.getName(), is("/ShardingSphere/rootInvoke/"));
        assertThat(spanData.getAttributes().get(AttributeKey.stringKey(OpenTelemetryConstants.COMPONENT)), is(OpenTelemetryConstants.COMPONENT_NAME));
        assertThat(spanData.getKind(), is(SpanKind.CLIENT));
        assertThat(spanData.getStatus().getStatusCode(), is(StatusCode.ERROR));
    }
}
