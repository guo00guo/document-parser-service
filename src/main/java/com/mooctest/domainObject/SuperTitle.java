package com.mooctest.domainObject;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

import java.math.BigInteger;

/**
 * @author guochao
 * @date 2020-05-11 15:40
 */
@Data
public class SuperTitle {
    private String paragraphText = null;
    private int lvl;       // 大纲级别
    private String numFmt;   // 标题格式
    private String numLevelText;    // 标题模板
    private BigInteger numIlvl;     // 标题深度
    private BigInteger numId;     // 标题级别

    private int FontSize;
    private String FontName = "宋体";
    private String asciiFontName = "";
    private String eastAsiaFontName = "";

    private boolean isBold = false;   // 是否加粗
    private boolean isItalic = false;   // 是否斜体
    private boolean highlighted = false;    // 是否高亮
    private boolean strike = false; // 是否存在单删除线
    private UnderlinePatterns underline = UnderlinePatterns.NONE;   // 是否存在下划线

    private int justification = 0;
    private boolean isInTable = false;
    private int fontAlignment;  // 字体对齐方式：1左对齐 2居中 3右对齐
    private boolean isTableRowEnd = false;
    private int paragraphID;
    private int indentFromLeft;
    private int indentFromRight;
    private int firstLineIndent;    // 首行缩进
    private String color = "000000";
    private ParagraphAlignment alignment = ParagraphAlignment.BOTH; // 段落对齐方式
}
