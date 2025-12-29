package org.example.wedevelop.core.parser;

/**
 * 代码解析器策略接口
 *
 * @param <T>
 */
public interface CodeParser<T> {

    /**
     * 解析代码内容
     *
     * @param codeContent
     * @return
     */
    T parseCode(String codeContent);
}
