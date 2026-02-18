package org.example.wedevelop.ai;

import dev.langchain4j.service.SystemMessage;
import org.example.wedevelop.model.enums.CodeGenTypeEnum;

/**
 * AI 代码生成类型智能路由服务
 * 使用结构化输出直接返回枚举类型
 */
public interface AiCodeGenTypeRoutingService {

    /**
     * 根据用户需求智能选择代码生成类型
     *
     * @param userPrompt
     * @return
     */
    @SystemMessage(fromResource = "prompt/codegen-routing-system-prompt.txt")
    CodeGenTypeEnum routeCodeGenType(String userPrompt);
}
