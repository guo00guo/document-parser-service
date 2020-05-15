package com.mooctest.domainObject.DocParser;

import com.mooctest.domainObject.SuperParagraph;
import com.mooctest.domainObject.SuperTable;
import lombok.Data;
import org.apache.poi.xwpf.usermodel.TableRowAlign;

import java.util.ArrayList;
import java.util.List;

@Data
public class DocTable extends SuperTable {
//    private String textBefore;
//    private String textAfter;
//    private DocParagraph paragraphBefore;
//    private DocParagraph paragraphAfter;
//    private int index;
    public List<List<List<DocParagraph>>> docTableContent = new ArrayList<>();

    public DocTable(String textBefore, String textAfter, SuperParagraph paragraphBefore, SuperParagraph paragraphAfter, int index, TableRowAlign alignment, List<List<List<DocParagraph>>> tableContent) {
        super(textBefore, textAfter, paragraphBefore, paragraphAfter, index, alignment);
        this.docTableContent = tableContent;
    }

    public DocTable(List<List<List<DocParagraph>>> tableContent) {
        this.docTableContent = tableContent;
    }

    public DocTable() {

    }
}
