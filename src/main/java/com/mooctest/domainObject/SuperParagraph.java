package com.mooctest.domainObject;

import lombok.Data;

/**
 * @author guochao
 * @date 2020-05-11 15:40
 */
@Data
public class SuperParagraph {
    private String paragraphText = null;
    private int paragraphID;
    private String FontName = "宋体";
    private String asciiFontName = "";
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isInTable = false;
    private String color = "000000";
    private String PdfTitle;
    private int level = 9;
}
