package org.example.wedevelop.langgraph4j.ai;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.annotation.Resource;

@Slf4j
@SpringBootTest
@ActiveProfiles("local")
public class AppNamingServiceTest {

    @Resource
    private AppNamingService appNamingService;

    @Test
    public void testGenerateAppName() {
        // 测试用例1：个人博客
        String prompt1 = "创建一个个人博客网站，需要有文章列表、详情页、分类标签、搜索功能和个人简介页面。文章支持Markdown格式，首页展示最新文章。";
        String appName1 = appNamingService.generateAppName(prompt1);
        log.info("测试1 - 输入: {}", prompt1);
        log.info("测试1 - 生成的应用名称: {}", appName1);

        // 测试用例2：电商网站
        String prompt2 = "创建一个在线购物商城，包含商品展示、购物车、订单管理、用户登录注册功能。";
        String appName2 = appNamingService.generateAppName(prompt2);
        log.info("测试2 - 输入: {}", prompt2);
        log.info("测试2 - 生成的应用名称: {}", appName2);

        // 测试用例3：企业官网
        String prompt3 = "创建一个科技公司官网，展示公司介绍、产品服务、新闻动态、联系方式。";
        String appName3 = appNamingService.generateAppName(prompt3);
        log.info("测试3 - 输入: {}", prompt3);
        log.info("测试3 - 生成的应用名称: {}", appName3);

        // 验证结果
        assert appName1 != null && !appName1.isEmpty() : "应用名称1不应为空";
        assert appName2 != null && !appName2.isEmpty() : "应用名称2不应为空";
        assert appName3 != null && !appName3.isEmpty() : "应用名称3不应为空";

        log.info("所有测试通过！");
    }
}
