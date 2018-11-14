/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.feature.KeyValueMap;

/**
 * Builder context holds services used by {@link FeatureBuilder}.
 *
 * This class is not thread-safe.
 */
public class BuilderContext {

    enum ArtifactMergeAlgorithm {
        LATEST, HIGHEST
    }

    /** The required feature provider */
    private final FeatureProvider provider;

    /** The optional artifact provider. */
    private ArtifactProvider artifactProvider;

    private final Map<String, KeyValueMap> extensionConfiguration = new HashMap<>();
    private final List<MergeHandler> mergeExtensions = new ArrayList<>();
    private final List<PostProcessHandler> postProcessExtensions = new ArrayList<>();
    private final KeyValueMap variables = new KeyValueMap();
    private final KeyValueMap frameworkProperties = new KeyValueMap();

    private ArtifactMergeAlgorithm merge = ArtifactMergeAlgorithm.HIGHEST;

    /**
     * Create a new context
     *
     * @param provider A provider providing the included features
     * @throws IllegalArgumentException If feature provider is {@code null}
     */
    public BuilderContext(final FeatureProvider provider) {
        if ( provider == null ) {
            throw new IllegalArgumentException("Provider must not be null");
        }
        this.provider = provider;
    }

    /**
     * Set the artifact provider
     *
     * @param ap An ArtifactProvider to resolve artifact IDs to files
     * @return The builder context
     */
    public BuilderContext setArtifactProvider(final ArtifactProvider ap) {
        this.artifactProvider = ap;
        return this;
    }

    /**
     * Add overwrites for the variables
     *
     * @param vars The overwrites
     * @return The builder context
     */
    public BuilderContext addVariablesOverwrites(final KeyValueMap vars) {
        this.variables.putAll(vars);
        return this;
    }

    /**
     * Add overwrites for the framework properties
     *
     * @param props The overwrites
     * @return The builder context
     */
    public BuilderContext addFrameworkPropertiesOverwrites(final KeyValueMap props) {
        this.frameworkProperties.putAll(props);
        return this;
    }

    /**
     * Add merge extensions
     *
     * @param extensions A list of merge extensions
     * @return The builder context
     */
    public BuilderContext addMergeExtensions(final MergeHandler... extensions) {
        mergeExtensions.addAll(Arrays.asList(extensions));
        return this;
    }

    /**
     * Add post process extensions
     *
     * @param extensions A list of extensions
     * @return The builder context
     */
    public BuilderContext addPostProcessExtensions(final PostProcessHandler... extensions) {
        postProcessExtensions.addAll(Arrays.asList(extensions));
        return this;
    }

    /**
     * Set the merge algorithm
     *
     * @param alg The algorithm
     * @return The builder context
     */
    public BuilderContext setMergeAlgorithm(final ArtifactMergeAlgorithm alg) {
        this.merge = alg;
        return this;
    }

    /**
     * Set a handler configuration
     *
     * @param The name of the handler
     * @param The configuration for the handler
     * @return The builder context
     */
    public BuilderContext setHandlerConfiguration(final String name, final KeyValueMap cfg) {
        this.extensionConfiguration.put(name, cfg);
        return this;
    }

    /**
     * Obtain the handler configuration.
     * 
     * @return The current handler configuration object. The key is the handler name
     *         and the value is a map of configuration values.
     */
    Map<String, KeyValueMap> getHandlerConfigurations() {
        return this.extensionConfiguration;
    }

    ArtifactProvider getArtifactProvider() {
        return this.artifactProvider;
    }

    KeyValueMap getVariablesOverwrites() {
        return  this.variables;
    }

    KeyValueMap getFrameworkPropertiesOverwrites() {
        return this.frameworkProperties;
    }

    /**
     * Get the feature provider.
     * @return The feature provider
     */
    FeatureProvider getFeatureProvider() {
        return this.provider;
    }

    ArtifactMergeAlgorithm getMergeAlgorithm() {
        return this.merge;
    }

    /**
     * Get the list of merge extensions
     * @return The list of merge extensions
     */
    List<MergeHandler> getMergeExtensions() {
        return this.mergeExtensions;
    }

    /**
     * Get the list of extension post processors
     * @return The list of post processors
     */
    List<PostProcessHandler> getPostProcessExtensions() {
        return this.postProcessExtensions;
    }

    /**
     * Clone the context and replace the feature provider
     * @param featureProvider The new feature provider
     * @return Cloned context
     */
    BuilderContext clone(final FeatureProvider featureProvider) {
        final BuilderContext ctx = new BuilderContext(featureProvider);
        ctx.setArtifactProvider(this.artifactProvider);
        ctx.variables.putAll(this.variables);
        ctx.frameworkProperties.putAll(this.frameworkProperties);
        ctx.mergeExtensions.addAll(mergeExtensions);
        ctx.postProcessExtensions.addAll(postProcessExtensions);
        ctx.merge = this.merge;
        return ctx;
    }
}
