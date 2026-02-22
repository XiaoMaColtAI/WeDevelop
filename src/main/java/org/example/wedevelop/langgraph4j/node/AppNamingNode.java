package org.example.wedevelop.langgraph4j.node;

import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;
import org.example.wedevelop.langgraph4j.ai.AppNamingService;
import org.example.wedevelop.langgraph4j.state.WorkflowContext;
import org.example.wedevelop.utils.SpringContextUtil;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class AppNamingNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 应用命名");
            String originalPrompt = context.getOriginalPrompt();
            String appName;
            try {
                AppNamingService namingService = SpringContextUtil.getBean(AppNamingService.class);
                appName = namingService.generateAppName(originalPrompt);
                log.info("应用名称生成成功: {}", appName);
            } catch (Exception e) {
                log.error("应用命名失败: {}", e.getMessage(), e);
                appName = "智能应用";
            }
            context.setCurrentStep("应用命名");
            context.setAppName(appName);
            return WorkflowContext.saveContext(context);
        });
    }
}
