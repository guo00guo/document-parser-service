package com.mooctest.domainObject;

import lombok.Data;

/**
 * @author guochao
 * @date 2020-05-11 15:46
 */
@Data
public class SuperTable {
    private String textBefore;
    private String textAfter;
    private SuperParagraph paragraphBefore;
    private SuperParagraph paragraphAfter;
    private int index;
//    private TableRowAlign alignment;


    public SuperTable(String textBefore, String textAfter, SuperParagraph paragraphBefore, SuperParagraph paragraphAfter, int index) {
        this.textBefore = textBefore;
        this.textAfter = textAfter;
        this.paragraphBefore = paragraphBefore;
        this.paragraphAfter = paragraphAfter;
        this.index = index;
    }

    public SuperTable() {

    }
}
