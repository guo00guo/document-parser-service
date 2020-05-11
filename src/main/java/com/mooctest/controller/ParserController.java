package com.mooctest.controller;

import com.mooctest.data.response.ResponseVO;
import com.mooctest.data.response.ServerCode;
import com.mooctest.domainObject.*;
import com.mooctest.service.ParserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author guochao
 * @date 2020-05-08 15:46
 */
@RestController
@RequestMapping("/api/word_parser")
@Slf4j
@Api(value="parser",tags="文档解析的相关接口")
public class ParserController {

    @Autowired
    private ParserService parserService;

    @RequestMapping(value = "/load_file", method = RequestMethod.POST)
    @ApiOperation(value="上传并解析文档",notes="上传并解析文档，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<List<SuperParagraph>> loadFile(@RequestPart(name = "file") MultipartFile uploadFile) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS,parserService.parserFile(uploadFile));
    }

    @RequestMapping(value = "/all_paragraphs", method = RequestMethod.POST)
    @ApiOperation(value="获取文档的所有段落信息",notes="获取文档的所有段落信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<List<SuperParagraph>> getAllPara(@RequestPart(name = "file") MultipartFile uploadFile) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getAllPara(uploadFile));
    }

    @RequestMapping(value = "/all_pics", method = RequestMethod.POST)
    @ApiOperation(value="获取文档的所有图片信息",notes="获取文档的所有图片信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<List<SuperPicture>> getAllPic(@RequestPart(name = "file") MultipartFile uploadFile) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getAllPicture(uploadFile));
    }

    @RequestMapping(value = "/all_tables", method = RequestMethod.POST)
    @ApiOperation(value="获取文档的所有表格信息",notes="获取文档的所有表格信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<List<SuperTable>> getAllTable(@RequestPart(name = "file") MultipartFile uploadFile) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getAllTable(uploadFile));
    }

    @RequestMapping(value = "/all_titles", method = RequestMethod.POST)
    @ApiOperation(value="获取文档的所有标题信息",notes="获取文档的所有标题信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<List<SuperTitle>> getAllTitle(@RequestPart(name = "file") MultipartFile uploadFile) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getAllTitle(uploadFile));
    }

    /**
     * 获取指定段落下的段落信息
     * @param uploadFile
     * @param paraId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/paragraph/{paragraph_id}", method = RequestMethod.POST)
    @ApiOperation(value="获取文档指定段落的段落信息",notes="获取文档指定标题下的所有段落信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<SuperParagraph> getParaInfoByParaId(@RequestPart(name = "file") MultipartFile uploadFile, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getParaInfoByParaId(uploadFile, paraId));
    }

    @RequestMapping(value = "/paragraph/{paragraph_id}/paragraph_style", method = RequestMethod.POST)
    @ApiOperation(value="获取文档指定段落的段落格式信息",notes="获取文档指定段落的段落格式信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<SuperParagraphStyle> getParaStyleByParaId(@RequestPart(name = "file") MultipartFile uploadFile, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getParaStyleByParaId(uploadFile, paraId));
    }

    @RequestMapping(value = "/paragraph/{paragraph_id}/font_style", method = RequestMethod.POST)
    @ApiOperation(value="获取文档指定段落的字体格式信息",notes="获取文档指定段落的字体格式信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<SuperFontStyle> getFontStyleByParaId(@RequestPart(name = "file") MultipartFile uploadFile, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getFontStyleByParaId(uploadFile, paraId));
    }

    /**
     * 获取指定标题下的所有段落信息
     * @param uploadFile
     * @param paraId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{paragraph_id}/all_paragraphs", method = RequestMethod.POST)
    @ApiOperation(value="获取文档指定标题下的所有段落信息",notes="获取文档指定标题下的所有段落信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<List<SuperParagraph>> getAllParaByTitleId(@RequestPart(name = "file") MultipartFile uploadFile, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getAllParaByTitleId(uploadFile, paraId));
    }

    @RequestMapping(value = "/{paragraph_id}/all_pics", method = RequestMethod.POST)
    @ApiOperation(value="获取文档指定标题下的所有图片信息",notes="获取文档指定标题下的所有图片信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<List<SuperPicture>> getAllPictureByTitleId(@RequestPart(name = "file") MultipartFile uploadFile, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getAllPictureByTitleId(uploadFile, paraId));
    }

    @RequestMapping(value = "/{paragraph_id}/all_table", method = RequestMethod.POST)
    @ApiOperation(value="获取文档指定标题下的所有表格信息",notes="获取文档指定标题下的所有表格信息，需参数", httpMethod = "POST")
    @ResponseBody
    public ResponseVO<List<SuperTable>> getAllTableByTitleId(@RequestPart(name = "file") MultipartFile uploadFile, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return new ResponseVO<>(ServerCode.SUCCESS, parserService.getAllTableByTitleId(uploadFile, paraId));
    }
}
