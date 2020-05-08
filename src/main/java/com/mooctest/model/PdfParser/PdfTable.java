package com.mooctest.model.PdfParser;

import java.util.ArrayList;
import java.util.List;


class PdfTable {
    private String textBefore;
    private String textAfter;
    private PdfParagraph paragraphBefore;
    private PdfParagraph paragraphAfter;
    private int index;
    public List<List<List<PdfParagraph>>> tableContent = new ArrayList<>();

    public String getTextBefore() {
        return textBefore;
    }

    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    public String getTextAfter() {
        return textAfter;
    }

    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }

    public PdfParagraph getParagraphBefore() {
        return paragraphBefore;
    }

    public void setParagraphBefore(PdfParagraph paragraphBefore) {
        this.paragraphBefore = paragraphBefore;
    }

    public PdfParagraph getParagraphAfter() {
        return paragraphAfter;
    }

    public void setParagraphAfter(PdfParagraph paragraphAfter) {
        this.paragraphAfter = paragraphAfter;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
