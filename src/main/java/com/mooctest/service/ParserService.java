package com.mooctest.service;

import com.mooctest.domainObject.SuperPicture;
import com.mooctest.domainObject.SuperParagraph;
import com.mooctest.domainObject.SuperTable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author guochao
 * @date 2020-05-08 17:30
 */

public interface ParserService {
    List<SuperParagraph> parserFile(MultipartFile uploadFile) throws IOException;

    List<SuperParagraph> getAllPara(MultipartFile uploadFile) throws IOException;

    List<SuperPicture> getAllPicture(MultipartFile uploadFile) throws IOException;

    List<SuperTable> getAllTable(MultipartFile uploadFile) throws IOException;

    List<SuperParagraph> getAllTitle(MultipartFile uploadFile) throws IOException;

    List<SuperParagraph> getAllParaByTitleId(MultipartFile uploadFile, Long paraId) throws IOException;

    List<SuperPicture> getAllPictureByTitleId(MultipartFile uploadFile, Long paraId) throws IOException;

    List<SuperTable> getAllTableByTitleId(MultipartFile uploadFile, Long paraId) throws IOException;

    SuperParagraph getParaInfoByParaId(MultipartFile uploadFile, Long paraId) throws IOException;

    SuperParagraph getFontInfoByParaId(MultipartFile uploadFile, Long paraId);
}
