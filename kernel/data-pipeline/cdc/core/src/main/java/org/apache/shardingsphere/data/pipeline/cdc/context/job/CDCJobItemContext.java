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

package org.apache.shardingsphere.data.pipeline.cdc.context.job;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.apache.shardingsphere.data.pipeline.api.datasource.PipelineDataSourceManager;
import org.apache.shardingsphere.data.pipeline.api.datasource.PipelineDataSourceWrapper;
import org.apache.shardingsphere.data.pipeline.api.job.JobStatus;
import org.apache.shardingsphere.data.pipeline.api.job.progress.InventoryIncrementalJobItemProgress;
import org.apache.shardingsphere.data.pipeline.api.job.progress.listener.PipelineJobProgressUpdatedParameter;
import org.apache.shardingsphere.data.pipeline.api.metadata.loader.PipelineTableMetaDataLoader;
import org.apache.shardingsphere.data.pipeline.cdc.config.job.CDCJobConfiguration;
import org.apache.shardingsphere.data.pipeline.cdc.config.task.CDCTaskConfiguration;
import org.apache.shardingsphere.data.pipeline.cdc.context.CDCProcessContext;
import org.apache.shardingsphere.data.pipeline.core.context.InventoryIncrementalJobItemContext;
import org.apache.shardingsphere.data.pipeline.core.metadata.loader.StandardPipelineTableMetaDataLoader;
import org.apache.shardingsphere.data.pipeline.core.task.IncrementalTask;
import org.apache.shardingsphere.data.pipeline.core.task.InventoryTask;

import java.util.Collection;
import java.util.LinkedList;

/**
 * CDC job item context.
 */
@RequiredArgsConstructor
@Getter
public final class CDCJobItemContext implements InventoryIncrementalJobItemContext {
    
    private final CDCJobConfiguration jobConfig;
    
    private final int shardingItem;
    
    @Setter
    private volatile boolean stopping;
    
    @Setter
    private volatile JobStatus status = JobStatus.RUNNING;
    
    private final InventoryIncrementalJobItemProgress initProgress;
    
    private final CDCProcessContext jobProcessContext;
    
    private final CDCTaskConfiguration taskConfig;
    
    private final PipelineDataSourceManager dataSourceManager;
    
    private final Collection<InventoryTask> inventoryTasks = new LinkedList<>();
    
    private final Collection<IncrementalTask> incrementalTasks = new LinkedList<>();
    
    private final LazyInitializer<PipelineDataSourceWrapper> sourceDataSourceLazyInitializer = new LazyInitializer<PipelineDataSourceWrapper>() {
        
        @Override
        protected PipelineDataSourceWrapper initialize() {
            return dataSourceManager.getDataSource(taskConfig.getDumperConfig().getDataSourceConfig());
        }
    };
    
    private final LazyInitializer<PipelineTableMetaDataLoader> sourceMetaDataLoaderLazyInitializer = new LazyInitializer<PipelineTableMetaDataLoader>() {
        
        @Override
        protected PipelineTableMetaDataLoader initialize() throws ConcurrentException {
            return new StandardPipelineTableMetaDataLoader(sourceDataSourceLazyInitializer.get());
        }
    };
    
    @Override
    public String getJobId() {
        return jobConfig.getJobId();
    }
    
    @Override
    public String getDataSourceName() {
        return taskConfig.getDumperConfig().getDataSourceName();
    }
    
    @Override
    public void onProgressUpdated(final PipelineJobProgressUpdatedParameter param) {
        // TODO to be implemented
    }
    
    /**
     * Get source data source.
     *
     * @return source data source
     */
    @SneakyThrows(ConcurrentException.class)
    public PipelineDataSourceWrapper getSourceDataSource() {
        return sourceDataSourceLazyInitializer.get();
    }
    
    @Override
    @SneakyThrows(ConcurrentException.class)
    public PipelineTableMetaDataLoader getSourceMetaDataLoader() {
        return sourceMetaDataLoaderLazyInitializer.get();
    }
    
    @Override
    public long getProcessedRecordsCount() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void updateInventoryRecordsCount(final long recordsCount) {
        // TODO to be implemented
    }
    
    @Override
    public long getInventoryRecordsCount() {
        return 0;
    }
}
