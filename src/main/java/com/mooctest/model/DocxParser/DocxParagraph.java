package com.mooctest.model.DocxParser;

class DocxParagraph {
    private String paragraphText = null;
    private int lvl;
    private int FontSize;
    private String FontName = "宋体";
    private String asciiFontName = "";
    private String eastAsiaFontName = "";
    private boolean isBold = false;
    private boolean isItalic = false;
    private int justification = 0;
    private boolean isInTable = false;
    private int lineSpacing;
    private int fontAlignment;
    private boolean isTableRowEnd = false;
    private int paragraphID;
    private int indentFromLeft;
    private int indentFromRight;
    private int firstLineIndent;
    private String color = "000000";

    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }

    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
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

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAsciiFontName() {
        return asciiFontName;
    }

    public void setAsciiFontName(String asciiFontName) {
        this.asciiFontName = asciiFontName;
    }

    public String getEastAsiaFontName() {
        return eastAsiaFontName;
    }

    public void setEastAsiaFontName(String eastAsiaFontName) {
        this.eastAsiaFontName = eastAsiaFontName;
    }
}
