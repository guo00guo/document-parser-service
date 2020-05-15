package com.mooctest.domainObject;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

import java.io.Serializable;

/**
 * @author guochao
 * @date 2020-05-11 18:12
 */
@Data
public class SuperFontStyle implements Serializable {
    private int FontSize;
    private String FontName = "宋体";
    private String asciiFontName = "";
    private String eastAsiaFontName = "";
    private boolean isBold = false;   // 是否加粗
    private boolean isItalic = false;   // 是否斜体
    private boolean highlighted = false;    // 是否高亮
    private boolean strike = false; // 是否存在单删除线
    private UnderlinePatterns underline = UnderlinePatterns.NONE;   // 是否存在下划线
    private int fontAlignment;  // 字体对齐方式：1左对齐 2居中 3右对齐
    private String color = "000000";
}
