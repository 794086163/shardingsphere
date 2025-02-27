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

package org.apache.shardingsphere.readwritesplitting.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.algorithm.ShardingSphereAlgorithmFactory;
import org.apache.shardingsphere.readwritesplitting.spi.ReadQueryLoadBalanceAlgorithm;
import org.apache.shardingsphere.infra.util.spi.ShardingSphereServiceLoader;
import org.apache.shardingsphere.infra.util.spi.type.required.RequiredSPIRegistry;
import org.apache.shardingsphere.infra.util.spi.type.typed.TypedSPIRegistry;

/**
 * Read query load-balance algorithm factory.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReadQueryLoadBalanceAlgorithmFactory {
    
    static {
        ShardingSphereServiceLoader.register(ReadQueryLoadBalanceAlgorithm.class);
    }
    
    /**
     * Create new instance of read query load-balance algorithm.
     *
     * @return created instance
     */
    public static ReadQueryLoadBalanceAlgorithm newInstance() {
        return RequiredSPIRegistry.getRegisteredService(ReadQueryLoadBalanceAlgorithm.class);
    }
    
    /**
     * Create new instance of read query load-balance algorithm.
     * 
     * @param readQueryLoadBalanceAlgorithmConfig read query load-balance algorithm configuration
     * @return created instance
     */
    public static ReadQueryLoadBalanceAlgorithm newInstance(final AlgorithmConfiguration readQueryLoadBalanceAlgorithmConfig) {
        return ShardingSphereAlgorithmFactory.createAlgorithm(readQueryLoadBalanceAlgorithmConfig, ReadQueryLoadBalanceAlgorithm.class);
    }
    
    /**
     * Judge whether contains read query load-balance algorithm.
     *
     * @param readQueryLoadBalanceAlgorithmType read query load-balance algorithm type
     * @return contains read query load-balance algorithm or not
     */
    public static boolean contains(final String readQueryLoadBalanceAlgorithmType) {
        return TypedSPIRegistry.findRegisteredService(ReadQueryLoadBalanceAlgorithm.class, readQueryLoadBalanceAlgorithmType).isPresent();
    }
}
