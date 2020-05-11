package com.mooctest.domainObject.DocParser;

import com.mooctest.domainObject.SuperParagraph;
import lombok.Data;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

@Data
class DocParagraph extends SuperParagraph {
    private String paragraphText = null;
    private int lvl;
    private int llvl;
    private int linfo;
    private int FontSize;
    private String FontName;
    private Paragraph paragraph = null;
    private XWPFParagraph xwpfParagraph = null;
    private boolean isBold = false;
    private boolean isItalic = false;
    private int justification = 0;
    private boolean isInTable = false;
    private String lineSpacing;
    private int fontAlignment;
    private boolean isTableRowEnd = false;
    private int paragraphID;
    private int indentFromLeft;
    private int indentFromRight;
    private int firstLineIndent;
}
