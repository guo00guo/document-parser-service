package com.mooctest.domainObject.DocxParser;

import com.mooctest.domainObject.SuperTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class DocxTable extends SuperTable {
    private String textBefore;
    private String textAfter;
    private DocxParagraph paragraphBefore;
    private DocxParagraph paragraphAfter;
    private int index;
    public List<List<List<DocxParagraph>>> tableContent = new ArrayList<>();
}