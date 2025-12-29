package org.example.wedevelop.core.parser;

import org.example.wedevelop.ai.model.HtmlCodeResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML 单文件代码解析器
 */
public class HtmlCodeParser implements CodeParser<HtmlCodeResult> {

    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

    /**
     * 解析 HTML 单文件代码
     *
     * @param codeContent
     * @return
     */
    @Override
    public HtmlCodeResult parseCode(String codeContent) {
        HtmlCodeResult result = new HtmlCodeResult();
        // 提取 HTML 代码
        String htmlCode = extractHtmlCode(codeContent);
        if (htmlCode != null && !htmlCode.trim().isEmpty()) {
            result.setHtmlCode(htmlCode.trim());
        } else {
            // 如果没有找到代码块，将整个内容作为 HTML
            result.setHtmlCode(codeContent.trim());
        }
        return result;
    }

    /**
     * 提取 HTML 代码内容
     *
     * @param content
     * @return
     */
    private String extractHtmlCode(String content) {
        Matcher matcher = HTML_CODE_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
