package com.mooctest.domainObject.DocxParser;

import com.mooctest.domainObject.SuperPicture;
import lombok.Data;

@Data
class DocxSuperPicture extends SuperPicture {
    private String textBefore;
    private String textAfter;
    private DocxParagraph paragraphBefore;
    private DocxParagraph paragraphAfter;
    private double height;
    private double width;
    private String suggestFileExtension;
    private String base64Content;
    private int dxaGoal;
    private int dyaGoal;
    private int paragraphID;
    private String fileName;
    private int index;
}
