package com.mooctest.domainObject.PdfParser;

import com.mooctest.domainObject.SuperParagraph;
import lombok.Data;

@Data
public class PdfParagraph extends SuperParagraph {
//    private String paragraphText = null;
//    private int paragraphID;
//    private String FontName = "宋体";
//    private String asciiFontName = "";
//    private boolean isBold = false;
//    private boolean isItalic = false;
//    private boolean isInTable = false;
//    private String color = "000000";
    private String PdfTitle;
    private int level = 9;

    public PdfParagraph(String pdfTitle, int level) {
        PdfTitle = pdfTitle;
        this.level = level;
    }

    public PdfParagraph() {

    }
}
