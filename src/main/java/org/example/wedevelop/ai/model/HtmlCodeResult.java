package org.example.wedevelop.ai.model;

import jdk.jfr.Description;
import lombok.Data;

@Data
@Description("生成 HTML 代码文件的结果")
public class HtmlCodeResult {

    @Description("生成的 HTML 代码")
    private String htmlCode;

    @Description("生成的 HTML 描述")
    private String description;
}
