package com.mooctest.model.PdfParser;

class PdfParagraph {
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

    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
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

    public String getTitle() {
        return PdfTitle;
    }

    public void setTitle(String PdfTitle) {
        this.PdfTitle = PdfTitle;
    }


    public boolean isInTable() {
        return isInTable;
    }

    public void setInTable(boolean inTable) {
        isInTable = inTable;
    }


    public int getParagraphID() {
        return paragraphID;
    }

    public void setParagraphID(int paragraphID) {
        this.paragraphID = paragraphID;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
