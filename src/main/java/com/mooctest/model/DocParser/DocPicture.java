package com.mooctest.model.DocParser;

class DocPicture {
    private String textBefore;
    private String textAfter;
    private int height;
    private int width;
    private String suggestFileExtension;
    private String base64Content;
    private int dxaGoal;
    private int dyaGoal;
    private int index;

    public String getTextBefore() {
        return textBefore;
    }

    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    public String getTextAfter() {
        return textAfter;
    }

    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public String getSuggestFileExtension() {
        return suggestFileExtension;
    }

    public void setSuggestFileExtension(String suggestFileExtension) {
        this.suggestFileExtension = suggestFileExtension;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public int getDxaGoal() {
        return dxaGoal;
    }

    public void setDxaGoal(int dxaGoal) {
        this.dxaGoal = dxaGoal;
    }

    public int getDyaGoal() {
        return dyaGoal;
    }

    public void setDyaGoal(int dyaGoal) {
        this.dyaGoal = dyaGoal;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
