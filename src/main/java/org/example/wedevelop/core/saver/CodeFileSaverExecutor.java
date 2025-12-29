package org.example.wedevelop.core.saver;

import org.example.wedevelop.ai.model.HtmlCodeResult;
import org.example.wedevelop.ai.model.MultiFileCodeResult;
import org.example.wedevelop.exception.BusinessException;
import org.example.wedevelop.exception.ErrorCode;
import org.example.wedevelop.model.enums.CodeGenTypeEnum;

import java.io.File;

public class CodeFileSaverExecutor {

    private static final HtmlCodeFileSaverTemplate htmlCodeFileSaver = new HtmlCodeFileSaverTemplate();

    private static final MultiFileCodeFileSaverTemplate multiFileCodeFileSaver = new MultiFileCodeFileSaverTemplate();

    /**
     * 执行代码保存
     *
     * @param codeResult
     * @param codeGenType
     * @return
     */
    public static File executeSaver(Object codeResult, CodeGenTypeEnum codeGenType) {
        return switch (codeGenType) {
            case HTML -> htmlCodeFileSaver.saveCode((HtmlCodeResult) codeResult);
            case MULTI_FILE -> multiFileCodeFileSaver.saveCode((MultiFileCodeResult) codeResult);
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR,"不支持的代码生成类型：" + codeGenType.getValue());
        };
    }
}
