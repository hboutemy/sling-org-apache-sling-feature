/*

Map<String,String> * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sling.feature.builder;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Context for an extension handler.
 */
@ProviderType
public interface HandlerContext {

    /**
     * Provide the optional artifact provider.
     * @return The artifact provider or {@code null}
     */
    ArtifactProvider getArtifactProvider();

    /**
     * Configuration for the handler
     * @return Map of provided configuration, or an empty map if there is no configuration.
     * Never {@code null}.
     */
    Map<String,String> getConfiguration();

    /**
     * Is this merging a prototype into the defining feature?
     * @return {@code true} if it is prototype processing
     * @since 1.3.0
     */
    boolean isPrototypeMerge();

    /**
     * Is this the first feature being merged in?
     * @return {@code true} if it is the first feature
     * @since 1.3.0
     */
    boolean isInitialMerge();
}
