package com.mooctest.domainObject;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * @author guochao
 * @date 2020-05-11 18:12
 */
@Data
public class SuperParagraphStyle {
    private int lvl;
    private int indentFromLeft;
    private int indentFromRight;
    private int firstLineIndent;    // 首行缩进
    private ParagraphAlignment alignment = ParagraphAlignment.BOTH; // 段落对齐方式
}