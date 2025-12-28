package org.example.wedevelop.ai.model;

import jdk.jfr.Description;
import lombok.Data;

@Data
@Description("生成多文件代码的结果")
public class MultiFileCodeResult {

    @Description("生成的 HTML 代码")
    private String htmlCode;

    @Description("生成的 CSS 样式代码")
    private String cssCode;

    @Description("生成的 JavaScript 代码")
    private String jsCode;

    @Description("生成的代码描述")
    private String description;
}
