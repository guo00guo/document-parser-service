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
}
