package com.mooctest.domainObject;

import lombok.Data;

/**
 * @author guochao
 * @date 2020-05-11 15:28
 */
@Data
public class SuperPicture {
    private String textBefore;
    private String textAfter;
    private SuperParagraph paragraphBefore;
    private SuperParagraph paragraphAfter;
    private String suggestFileExtension;
    private String base64Content;
    private int dxaGoal;
    private int dyaGoal;
    private int paragraphID;
    private int index;

    // doc 没有
    private String fileName;

    public SuperPicture(String textBefore, String textAfter, SuperParagraph paragraphBefore, SuperParagraph paragraphAfter, String suggestFileExtension, String base64Content, int dxaGoal, int dyaGoal, int paragraphID, int index, String fileName) {
        this.textBefore = textBefore;
        this.textAfter = textAfter;
        this.paragraphBefore = paragraphBefore;
        this.paragraphAfter = paragraphAfter;
        this.suggestFileExtension = suggestFileExtension;
        this.base64Content = base64Content;
        this.dxaGoal = dxaGoal;
        this.dyaGoal = dyaGoal;
        this.paragraphID = paragraphID;
        this.index = index;
        this.fileName = fileName;
    }

    public SuperPicture() {

    }
}
