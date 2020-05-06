package FileParser.DocParser;

import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

class DocParagraph {
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

    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
    }

    public Paragraph getParagraph() {
        return paragraph;
    }

    public void setParagraph(Paragraph paragraph) {
        this.paragraph = paragraph;
    }

    public String getFontName() {
        return FontName;
    }

    public void setFontName(String fontName) {
        FontName = fontName;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
    }


    public int getJustification() {
        return justification;
    }

    public void setJustification(int justification) {
        this.justification = justification;
    }

    public boolean isInTable() {
        return isInTable;
    }

    public void setInTable(boolean inTable) {
        isInTable = inTable;
    }

    public String getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(String lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public boolean isTableRowEnd() {
        return isTableRowEnd;
    }

    public void setTableRowEnd(boolean tableRowEnd) {
        isTableRowEnd = tableRowEnd;
    }

    public int getParagraphID() {
        return paragraphID;
    }

    public void setParagraphID(int paragraphID) {
        this.paragraphID = paragraphID;
    }

    public int getFontAlignment() {
        return fontAlignment;
    }

    public void setFontAlignment(int fontAlignment) {
        this.fontAlignment = fontAlignment;
    }

    public int getFirstLineIndent() {
        return firstLineIndent;
    }

    public void setFirstLineIndent(int firstLineIndent) {
        this.firstLineIndent = firstLineIndent;
    }

    public int getLlvl() {
        return llvl;
    }

    public void setLlvl(int llvl) {
        this.llvl = llvl;
    }

    public int getLinfo() {
        return linfo;
    }

    public void setLinfo(int linfo) {
        this.linfo = linfo;
    }

    public int getIndentFromLeft() {
        return indentFromLeft;
    }

    public void setIndentFromLeft(int indentFromLeft) {
        this.indentFromLeft = indentFromLeft;
    }

    public int getIndentFromRight() {
        return indentFromRight;
    }

    public void setIndentFromRight(int indentFromRight) {
        this.indentFromRight = indentFromRight;
    }

    public XWPFParagraph getXwpfParagraph() {
        return xwpfParagraph;
    }

    public void setXwpfParagraph(XWPFParagraph xwpfParagraph) {
        this.xwpfParagraph = xwpfParagraph;
    }
}
