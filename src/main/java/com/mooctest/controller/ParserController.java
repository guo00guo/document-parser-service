package com.mooctest.controller;

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

    @RequestMapping(value = "/load_file", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ApiOperation(value="上传并解析文档",notes="上传并解析文档，需参数", httpMethod = "POST")
    @ResponseBody
    public String loadFile(@RequestPart(name = "file") MultipartFile uploadFile) throws IOException {
        return parserService.parserFile(uploadFile);
    }

    @RequestMapping(value = "/{token}/all_paragraphs", method = RequestMethod.GET)
    @ApiOperation(value="获取文档的所有段落信息",notes="获取文档的所有段落信息，需参数", httpMethod = "GET")
    @ResponseBody
    public List<SuperParagraph> getAllPara(@PathVariable(name = "token") String token){
        return parserService.getAllPara(token);
    }

    @RequestMapping(value = "/{token}/all_pics", method = RequestMethod.GET)
    @ApiOperation(value="获取文档的所有图片信息",notes="获取文档的所有图片信息，需参数", httpMethod = "GET")
    @ResponseBody
    public List<SuperPicture> getAllPic(@PathVariable(name = "token") String token) throws IOException {
        return parserService.getAllPicture(token);
    }

    @RequestMapping(value = "/{token}/all_tables", method = RequestMethod.GET)
    @ApiOperation(value="获取文档的所有表格信息",notes="获取文档的所有表格信息，需参数", httpMethod = "GET")
    @ResponseBody
    public List<SuperTable> getAllTable(@PathVariable(name = "token") String token) throws IOException {
        return parserService.getAllTable(token);
    }

    @RequestMapping(value = "/{token}/all_titles", method = RequestMethod.GET)
    @ApiOperation(value="获取文档的所有标题信息",notes="获取文档的所有标题信息，需参数", httpMethod = "GET")
    @ResponseBody
    public List<SuperParagraph> getAllTitle(@PathVariable(name = "token") String token) throws IOException {
        return parserService.getAllTitle(token);
    }

    /**
     * 获取指定段落下的段落信息
     * @param token
     * @param paraId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{token}/paragraph/{paragraph_id}", method = RequestMethod.GET)
    @ApiOperation(value="获取文档指定段落的段落信息",notes="获取文档指定标题下的所有段落信息，需参数", httpMethod = "GET")
    @ResponseBody
    public SuperParagraph getParaInfoByParaId(@PathVariable(name = "token") String token, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return parserService.getParaInfoByParaId(token, paraId);
    }

    @RequestMapping(value = "/{token}/paragraph/{paragraph_id}/paragraph_style", method = RequestMethod.GET)
    @ApiOperation(value="获取文档指定段落的段落格式信息",notes="获取文档指定段落的段落格式信息，需参数", httpMethod = "GET")
    @ResponseBody
    public SuperParagraphStyle getParaStyleByParaId(@PathVariable(name = "token") String token, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return parserService.getParaStyleByParaId(token, paraId);
    }

    @RequestMapping(value = "/{token}/paragraph/{paragraph_id}/font_style", method = RequestMethod.GET)
    @ApiOperation(value="获取文档指定段落的字体格式信息",notes="获取文档指定段落的字体格式信息，需参数", httpMethod = "GET")
    @ResponseBody
    public SuperFontStyle getFontStyleByParaId(@PathVariable(name = "token") String token, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return parserService.getFontStyleByParaId(token, paraId);
    }

    /**
     * 获取指定标题下的所有段落信息
     * @param token
     * @param paraId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{token}/{paragraph_id}/all_paragraphs", method = RequestMethod.GET)
    @ApiOperation(value="获取文档指定标题下的所有段落信息",notes="获取文档指定标题下的所有段落信息，需参数", httpMethod = "GET")
    @ResponseBody
    public List<SuperParagraph> getAllParaByTitleId(@PathVariable(name = "token") String token, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return parserService.getAllParaByTitleId(token, paraId);
    }

    @RequestMapping(value = "/{token}/{paragraph_id}/all_pics", method = RequestMethod.GET)
    @ApiOperation(value="获取文档指定标题下的所有图片信息",notes="获取文档指定标题下的所有图片信息，需参数", httpMethod = "GET")
    @ResponseBody
    public List<SuperPicture> getAllPictureByTitleId(@PathVariable(name = "token") String token, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return parserService.getAllPictureByTitleId(token, paraId);
    }

    @RequestMapping(value = "/{token}/{paragraph_id}/all_table", method = RequestMethod.GET)
    @ApiOperation(value="获取文档指定标题下的所有表格信息",notes="获取文档指定标题下的所有表格信息，需参数", httpMethod = "GET")
    @ResponseBody
    public List<SuperTable> getAllTableByTitleId(@PathVariable(name = "token") String token, @PathVariable("paragraph_id") Long paraId) throws IOException {
        return parserService.getAllTableByTitleId(token, paraId);
    }
}
