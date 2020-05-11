package com.mooctest.domainObject.DocParser;

import com.mooctest.domainObject.SuperTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class DocTable extends SuperTable {
    private String textBefore;
    private String textAfter;
    private DocParagraph paragraphBefore;
    private DocParagraph paragraphAfter;
    public List<DocParagraph> docParagraphs = new ArrayList<DocParagraph>();
}
