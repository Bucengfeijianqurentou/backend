package com.gb.backend.service.impl;

import com.gb.backend.entity.dto.AiMenuRequestDTO;
import com.gb.backend.entity.vo.AiMenuResponseVO;
import com.gb.backend.service.AiMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AI菜单服务实现类
 */
@Slf4j
@Service
public class AiMenuServiceImpl implements AiMenuService {
    
    // 注入由Spring AI自动配置好的AI模型客户端
    private final ChatClient chatClient;
    
    // 注入由Spring AI自动配置好的向量数据库客户端
    private final VectorStore vectorStore;
    
    @Autowired
    public AiMenuServiceImpl(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }
    
    /**
     * 这是需要向评委展示的核心方法
     * @param userQuery 例如："为10岁的孩子设计一份秋季午餐菜单"
     * @return AI生成的结果
     */
    @Override
    public String generateMenu(String userQuery) {
        try {
            // 步骤1: 检索(R) - 从《膳食指南》知识库中查找最相关的3条知识
            List<Document> relevantDocs = vectorStore.similaritySearch(
                SearchRequest.query(userQuery).withTopK(3)
            );
            // 将检索到的知识拼接成字符串，作为上下文
            String context = relevantDocs.stream()
                                         .map(Document::getContent)
                                         .collect(Collectors.joining("\n"));
            
            // 步骤2: 增强(A) - 使用提示词模板，将知识与用户需求结合
            String promptTemplate = """
                您是一位专业的儿童营养师。请严格参考以下【权威营养指南】，
                为用户的【具体需求】生成一份科学菜单。
                
                【权威营养指南】:
                {context}
                
                【具体需求】:
                {query}
                
                请按照以下格式输出：
                1. 推荐菜品：列出具体的菜品名称
                2. 营养搭配：说明营养均衡的考虑
                3. 制作建议：简要的制作要点
                """;
            
            // 步骤3: 生成(G) - 创建最终指令并调用AI
            Prompt finalPrompt = new PromptTemplate(promptTemplate)
                    .create(Map.of("context", context, "query", userQuery));
            
            // 修正：使用正确的Spring AI API调用方式
            return chatClient.prompt(finalPrompt).call().content();
            
        } catch (Exception e) {
            log.error("AI菜单生成失败: {}", e.getMessage(), e);
            return "抱歉，AI菜单生成服务暂时不可用，请稍后重试。";
        }
    }
    
    @Override
    public AiMenuResponseVO generateMenu(AiMenuRequestDTO request) {
        AiMenuResponseVO response = new AiMenuResponseVO();
        response.setTimestamp(System.currentTimeMillis());
        
        try {
            // 构建完整的查询字符串
            StringBuilder queryBuilder = new StringBuilder(request.getUserQuery());
            if (request.getMealType() != null) {
                queryBuilder.append("，餐次类型：").append(request.getMealType());
            }
            if (request.getPeopleCount() != null) {
                queryBuilder.append("，用餐人数：").append(request.getPeopleCount()).append("人");
            }
            if (request.getSpecialRequirements() != null) {
                queryBuilder.append("，特殊要求：").append(request.getSpecialRequirements());
            }
            
            String fullQuery = queryBuilder.toString();
            String menuContent = generateMenu(fullQuery);
            
            response.setMenuContent(menuContent);
            response.setSuccess(true);
            
            // 简单解析生成的内容，提取菜品列表（这里可以根据实际AI返回格式调整）
            List<String> dishes = extractDishesFromContent(menuContent);
            response.setRecommendedDishes(dishes);
            
            // 提取营养建议
            String nutritionAdvice = extractNutritionAdvice(menuContent);
            response.setNutritionAdvice(nutritionAdvice);
            
        } catch (Exception e) {
            log.error("AI菜单生成失败: {}", e.getMessage(), e);
            response.setSuccess(false);
            response.setErrorMessage("AI菜单生成服务暂时不可用，请稍后重试。");
        }
        
        return response;
    }
    
    /**
     * 从AI生成的内容中提取菜品列表
     */
    private List<String> extractDishesFromContent(String content) {
        // 这里是一个简单的实现，实际可以根据AI返回的格式进行优化
        try {
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (line.contains("推荐菜品") || line.contains("菜品")) {
                    String dishLine = line.replaceAll(".*[:：]", "").trim();
                    return Arrays.asList(dishLine.split("[,，、]"));
                }
            }
        } catch (Exception e) {
            log.warn("提取菜品列表失败: {}", e.getMessage());
        }
        return Arrays.asList("智能推荐菜品");
    }
    
    /**
     * 从AI生成的内容中提取营养建议
     */
    private String extractNutritionAdvice(String content) {
        try {
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (line.contains("营养") && (line.contains("搭配") || line.contains("建议"))) {
                    return line.replaceAll(".*[:：]", "").trim();
                }
            }
        } catch (Exception e) {
            log.warn("提取营养建议失败: {}", e.getMessage());
        }
        return "营养均衡，搭配合理";
    }
}