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

package org.apache.shardingsphere.proxy.backend.handler.distsql.ral;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.distsql.parser.statement.ral.UpdatableGlobalRuleRALStatement;
import org.apache.shardingsphere.distsql.handler.update.GlobalRuleRALUpdater;
import org.apache.shardingsphere.infra.util.spi.ShardingSphereServiceLoader;
import org.apache.shardingsphere.infra.util.spi.type.typed.TypedSPIRegistry;
import org.apache.shardingsphere.proxy.backend.handler.ProxyBackendHandler;

import java.util.Properties;

/**
 * Updatable RAL backend handler factory for global rule.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UpdatableGlobalRuleRALBackendHandlerFactory {
    
    static {
        ShardingSphereServiceLoader.register(GlobalRuleRALUpdater.class);
    }
    
    /**
     * Create new instance of updatable RAL backend handler for global rule.
     *
     * @param sqlStatement updatable RAL statement for global rule
     * @return created instance
     */
    public static ProxyBackendHandler newInstance(final UpdatableGlobalRuleRALStatement sqlStatement) {
        GlobalRuleRALUpdater updater = TypedSPIRegistry.getRegisteredService(GlobalRuleRALUpdater.class, sqlStatement.getClass().getCanonicalName(), new Properties());
        return new UpdatableGlobalRuleRALBackendHandler(sqlStatement, updater);
    }
}
