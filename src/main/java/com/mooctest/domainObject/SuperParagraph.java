package com.mooctest.domainObject;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author guochao
 * @date 2020-05-11 15:40
 */
@Data
public class SuperParagraph implements Serializable {
    private String paragraphText = null;
    private int lvl;    // 大纲级别
    private int fontSize;
    private String fontName = "宋体";
    private String asciiFontName = "";
    private String eastAsiaFontName = "";

    private boolean bold = false;   // 是否加粗
    private boolean italic = false;   // 是否斜体
    private boolean highlighted = false;    // 是否高亮
    private boolean strike = false; // 是否存在单删除线

    // doc 有 docx 没有
//    private String justification;  // 0=left, 1=center, 2=right, 3=left and right
//    private boolean tableRowEnd = false;

    private boolean inTable = false;
    private int paragraphID;
    private int indentFromLeft;
    private int indentFromRight;
    private int firstLineIndent;    // 首行缩进
    private String color = "000000";

    // 单元格合并信息
    private int rowspan;    // 合并的行数
    private int colspan;    // 合并的列数

    // doc 没有
    private String numFmt;   // 标题格式
    private String numLevelText;    // 标题模板
    private BigInteger numIlvl;     // 标题深度
    private BigInteger numId;     // 标题级别
//    private ParagraphAlignment alignment = ParagraphAlignment.BOTH; // 段落对齐方式
//    private UnderlinePatterns underline = UnderlinePatterns.NONE;   // 是否存在下划线


    public SuperParagraph(String paragraphText, int lvl, int fontSize, String fontName, String asciiFontName, String eastAsiaFontName, boolean bold, boolean italic, boolean highlighted, boolean strike, boolean inTable, int paragraphID, int indentFromLeft, int indentFromRight, int firstLineIndent, String color, int rowspan, int colspan, String numFmt, String numLevelText, BigInteger numIlvl, BigInteger numId) {
        this.paragraphText = paragraphText;
        this.lvl = lvl;
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.asciiFontName = asciiFontName;
        this.eastAsiaFontName = eastAsiaFontName;
        this.bold = bold;
        this.italic = italic;
        this.highlighted = highlighted;
        this.strike = strike;
        this.inTable = inTable;
        this.paragraphID = paragraphID;
        this.indentFromLeft = indentFromLeft;
        this.indentFromRight = indentFromRight;
        this.firstLineIndent = firstLineIndent;
        this.color = color;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.numFmt = numFmt;
        this.numLevelText = numLevelText;
        this.numIlvl = numIlvl;
        this.numId = numId;
    }

    public SuperParagraph() {

    }
}
