package org.example.wedevelop.ai;

import jakarta.annotation.Resource;
import org.example.wedevelop.core.AiCodeGeneratorFacade;
import org.example.wedevelop.model.enums.CodeGenTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class AiCodeGeneratorFacadeTest {

    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    void generateAndSaveCode() {
        File file = aiCodeGeneratorFacade.generateAndSaveCode("个人博客，不超过100行代码", CodeGenTypeEnum.HTML);
        Assertions.assertNotNull(file);
    }
}
