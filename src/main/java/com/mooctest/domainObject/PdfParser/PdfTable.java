package com.mooctest.domainObject.PdfParser;

import com.mooctest.domainObject.SuperParagraph;
import com.mooctest.domainObject.SuperTable;
import lombok.Data;
import org.apache.poi.xwpf.usermodel.TableRowAlign;

import java.util.ArrayList;
import java.util.List;

@Data
public class PdfTable extends SuperTable {
//    private String textBefore;
//    private String textAfter;
//    private PdfParagraph paragraphBefore;
//    private PdfParagraph paragraphAfter;
//    private int index;
    public List<List<List<PdfParagraph>>> pdfTableContent = new ArrayList<>();

    public PdfTable(String textBefore, String textAfter, SuperParagraph paragraphBefore, SuperParagraph paragraphAfter, int index, TableRowAlign alignment, List<List<List<PdfParagraph>>> tableContent) {
        super(textBefore, textAfter, paragraphBefore, paragraphAfter, index, alignment);
        this.pdfTableContent = tableContent;
    }

    public PdfTable(List<List<List<PdfParagraph>>> tableContent) {
        this.pdfTableContent = tableContent;
    }
}
