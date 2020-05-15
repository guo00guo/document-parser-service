package com.mooctest.domainObject.DocParser;

import com.mooctest.domainObject.SuperParagraph;
import lombok.Data;

import java.io.Serializable;

@Data
public class DocParagraph extends SuperParagraph implements Serializable {
//    private int lvl;
    private int llvl;
    private int linfo;
//    private int FontSize;
//    private String FontName;

//    private boolean isBold = false;
//    private boolean isItalic = false;
//    private int justification = 0;
//    private boolean isInTable = false;
    private String lineSpacing;
    private int fontAlignment;
//    private boolean isTableRowEnd = false;
//    private int paragraphID;
//    private int indentFromLeft;
//    private int indentFromRight;
//    private int firstLineIndent;
    private int underline;


//    private Paragraph paragraph = null;
//    private XWPFParagraph xwpfParagraph = null;


    public DocParagraph(int llvl, int linfo, String lineSpacing, int fontAlignment, int underline) {
        super();
        this.llvl = llvl;
        this.linfo = linfo;
        this.lineSpacing = lineSpacing;
        this.fontAlignment = fontAlignment;
        this.underline = underline;
    }

    public DocParagraph() {
        super();
    }
}
