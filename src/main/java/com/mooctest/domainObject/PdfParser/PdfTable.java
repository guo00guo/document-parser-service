package com.mooctest.domainObject.PdfParser;

import com.mooctest.domainObject.SuperTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class PdfTable extends SuperTable {
    private String textBefore;
    private String textAfter;
    private PdfParagraph paragraphBefore;
    private PdfParagraph paragraphAfter;
    private int index;
    public List<List<List<PdfParagraph>>> tableContent = new ArrayList<>();
}
