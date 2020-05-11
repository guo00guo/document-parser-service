package com.mooctest.domainObject.PdfParser;


import com.mooctest.domainObject.SuperPicture;
import lombok.Data;

@Data
class PdfSuperPicture extends SuperPicture {
    private String textBefore;
    private String textAfter;
    private PdfParagraph paragraphBefore;
    private PdfParagraph paragraphAfter;
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
