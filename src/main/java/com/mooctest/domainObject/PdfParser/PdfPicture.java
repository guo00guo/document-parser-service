package com.mooctest.domainObject.PdfParser;


import com.mooctest.domainObject.SuperParagraph;
import com.mooctest.domainObject.SuperPicture;
import lombok.Data;

@Data
public class PdfPicture extends SuperPicture {
    private double height;
    private double width;
//    private String suggestFileExtension;
//    private String base64Content;
//    private int dxaGoal;
//    private int dyaGoal;
//    private String fileName;
//    private int index;
    private int size;

    // 无
//    private String textBefore;
//    private String textAfter;
//    private PdfParagraph paragraphBefore;
//    private PdfParagraph paragraphAfter;
//    private int paragraphID;

    public PdfPicture(String textBefore, String textAfter, SuperParagraph paragraphBefore, SuperParagraph paragraphAfter, String suggestFileExtension, String base64Content, int dxaGoal, int dyaGoal, int paragraphID, int index, String fileName, double height, double width, int size) {
        super(textBefore, textAfter, paragraphBefore, paragraphAfter, suggestFileExtension, base64Content, dxaGoal, dyaGoal, paragraphID, index, fileName);
        this.height = height;
        this.width = width;
        this.size = size;
    }

    public PdfPicture(double height, double width, int size) {
        this.height = height;
        this.width = width;
        this.size = size;
    }

    public PdfPicture() {

    }
}
