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
    private String suggestFileExtension;
    private String base64Content;
    private int dxaGoal;
    private int dyaGoal;
    private int paragraphID;
    private String fileName;
    private int index;
}
