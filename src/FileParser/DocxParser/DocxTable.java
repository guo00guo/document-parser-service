package FileParser.DocxParser;

import java.util.ArrayList;
import java.util.List;


class DocxTable {
    private String textBefore;
    private String textAfter;
    private DocxParagraph paragraphBefore;
    private DocxParagraph paragraphAfter;
    private int index;
    public List<List<List<DocxParagraph>>> tableContent = new ArrayList<>();

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

    public DocxParagraph getParagraphBefore() {
        return paragraphBefore;
    }

    public void setParagraphBefore(DocxParagraph paragraphBefore) {
        this.paragraphBefore = paragraphBefore;
    }

    public DocxParagraph getParagraphAfter() {
        return paragraphAfter;
    }

    public void setParagraphAfter(DocxParagraph paragraphAfter) {
        this.paragraphAfter = paragraphAfter;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
