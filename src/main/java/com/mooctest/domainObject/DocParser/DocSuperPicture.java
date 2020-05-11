package com.mooctest.domainObject.DocParser;

import com.mooctest.domainObject.SuperPicture;
import lombok.Data;

@Data
class DocSuperPicture extends SuperPicture {
    private String textBefore;
    private String textAfter;
    private int height;
    private int width;
    private String suggestFileExtension;
    private String base64Content;
    private int dxaGoal;
    private int dyaGoal;
    private int index;
}
