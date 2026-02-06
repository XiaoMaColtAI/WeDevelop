package org.example.wedevelop.ai;

import jakarta.annotation.Resource;
import org.example.wedevelop.ai.model.HtmlCodeResult;
import org.example.wedevelop.ai.model.MultiFileCodeResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiCodeGeneratorServiceTest {

	@Resource
	private AiCodeGeneratorService aiCodeGeneratorService;
	
	@Test
	void generateHtmlCode() {
		HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode(1, "做一个笔记小工具，不超过100行代码");
		Assertions.assertNotNull(result);
	}
	
	@Test
	void generateMultiFileCode() {
		MultiFileCodeResult multiFileCode = aiCodeGeneratorService.generateMultiFileCode("做一个简单的留言板");
		Assertions.assertNotNull(multiFileCode);
	}

	@Test
	void testChatMemory() {
		HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("做一个简短的留言板，不超过50行代码");
		Assertions.assertNotNull(result);
		result = aiCodeGeneratorService.generateHtmlCode("不要写代码，告诉我你刚刚做了什么？");
		Assertions.assertNotNull(result);
		result = aiCodeGeneratorService.generateHtmlCode("做一个简短的留言板，不超过50行代码？");
		Assertions.assertNotNull(result);
		result = aiCodeGeneratorService.generateHtmlCode("不要写代码，告诉我你刚刚做了什么？");
		Assertions.assertNotNull(result);
	}
}