package com.gb.backend.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.ollama.management.ModelManagementOptions;

import java.util.Collections;

/**
 * Spring AI 配置类
 */
@Configuration
public class SpringAiConfig {
    
    @Value("${spring.ai.ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;
    
    @Value("${spring.ai.ollama.chat.options.model:qwen2.5}")
    private String modelName;
    
    @Value("${spring.ai.ollama.chat.options.temperature:0.7}")
    private Double temperature;
    
    /**
     * 配置 Ollama API
     */
    @Bean
    public OllamaApi ollamaApi() {
        return new OllamaApi(ollamaBaseUrl);
    }
    
    /**
     * 配置 ObservationRegistry
     */
    @Bean
    public ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }
    
    /**
     * 配置 FunctionCallbackContext
     */
    @Bean
    public FunctionCallbackContext functionCallbackContext() {
        return new FunctionCallbackContext();
    }
    
    /**
     * 配置 Ollama Chat Model
     */
    @Bean
    public OllamaChatModel ollamaChatModel(OllamaApi ollamaApi, 
                                          ObservationRegistry observationRegistry,
                                          FunctionCallbackContext functionCallbackContext) {
        return new OllamaChatModel(ollamaApi, 
                                 OllamaOptions.create()
                                     .withModel(modelName)
                                     .withTemperature(temperature),
                                 functionCallbackContext,
                                 Collections.emptyList(),
                                 observationRegistry,
                                 ModelManagementOptions.defaults());
    }
    
    /**
     * 配置 Ollama Embedding Model
     */
    @Bean
    public OllamaEmbeddingModel ollamaEmbeddingModel(OllamaApi ollamaApi,
                                                    ObservationRegistry observationRegistry) {
        return new OllamaEmbeddingModel(ollamaApi, 
                                      OllamaOptions.create().withModel(modelName),
                                      observationRegistry,
                                      ModelManagementOptions.defaults());
    }
    
    /**
     * 配置 ChatClient
     */
    @Bean
    public ChatClient chatClient(OllamaChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
    
    /**
     * 配置 VectorStore
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    }
}