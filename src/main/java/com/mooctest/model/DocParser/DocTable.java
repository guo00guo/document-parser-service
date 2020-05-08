package com.mooctest.model.DocParser;

import java.util.ArrayList;
import java.util.List;


class DocTable {
    private String textBefore;
    private String textAfter;
    private DocParagraph paragraphBefore;
    private DocParagraph paragraphAfter;
    public List<DocParagraph> docParagraphs = new ArrayList<DocParagraph>();

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

    public DocParagraph getParagraphBefore() {
        return paragraphBefore;
    }

    public void setParagraphBefore(DocParagraph paragraphBefore) {
        this.paragraphBefore = paragraphBefore;
    }

    public DocParagraph getParagraphAfter() {
        return paragraphAfter;
    }

    public void setParagraphAfter(DocParagraph paragraphAfter) {
        this.paragraphAfter = paragraphAfter;
    }
}
