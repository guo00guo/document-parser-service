package com.mooctest.service;

import com.mooctest.data.StyleWrapper;
import com.mooctest.domainObject.*;
import com.mooctest.exception.HttpBadRequestException;
import com.mooctest.factory.WordParserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author guochao
 * @date 2020-05-08 17:30
 */
@Service
@Slf4j
public class ParserServiceImpl implements ParserService {

    @Override
    public List<SuperParagraph> parserFile(MultipartFile uploadFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        WordParser wordParser = parseFile(uploadFile);

        List<SuperParagraph> paragraphsPre = wordParser.getAllParagraphs();
        int i = 0;
        for (SuperParagraph paragraph : paragraphsPre) {
            System.out.println(i + " " + paragraph.toString());
            i++;
        }

        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
        return paragraphs;
    }

    @Override
    public List<SuperParagraph> getAllPara(MultipartFile uploadFile) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
        return paragraphs;
    }

    @Override
    public List<SuperPicture> getAllPicture(MultipartFile uploadFile) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperPicture> allSuperPictures = wordParser.getAllPictures();
        return allSuperPictures;
    }

    @Override
    public List<SuperTable> getAllTable(MultipartFile uploadFile) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperTable> allSuperTables = wordParser.getAllTables();
        return allSuperTables;
    }

    @Override
    public List<SuperTitle> getAllTitle(MultipartFile uploadFile) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperTitle> allHeads = wordParser.getAllHeads().stream().map(para -> StyleWrapper.wrapperTitle(para)).collect(Collectors.toList());
        return allHeads;
    }

    @Override
    public List<SuperParagraph> getAllParaByTitleId(MultipartFile uploadFile, Long paraId) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperParagraph> allHeads = wordParser.getAllHeads();
        // 检验标题ID是否在文档标题的所属范围内
        checkParaIdInAllTitles(allHeads, paraId);
        int curTitleID = allHeads.get((int) (paraId - 1)).getParagraphID();
        // 判断是否存在后一个标题
        int nextTitleID = getNextTitleId(allHeads, paraId);
        System.out.println("标题所在段落ID区间：" + curTitleID + "  " + nextTitleID);
        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs().stream().filter(paragraph -> paragraph.getParagraphID() > curTitleID && paragraph.getParagraphID() < nextTitleID).collect(Collectors.toList());
        return paragraphs;
    }

    @Override
    public List<SuperPicture> getAllPictureByTitleId(MultipartFile uploadFile, Long paraId) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperParagraph> allHeads = wordParser.getAllHeads();
        // 检验标题ID是否在文档标题的所属范围内
        checkParaIdInAllTitles(allHeads, paraId);
        int curTitleID = allHeads.get((int) (paraId - 1)).getParagraphID();
        // 判断是否存在后一个标题
        int nextTitleID = getNextTitleId(allHeads, paraId);
        System.out.println("标题所在段落ID区间：" + curTitleID + "  " + nextTitleID);
        List<SuperPicture> pictures = wordParser.getAllPictures().stream().filter(picture -> picture.getParagraphID() > curTitleID && picture.getParagraphID() < nextTitleID).collect(Collectors.toList());
        return pictures;
    }

    @Override
    public List<SuperTable> getAllTableByTitleId(MultipartFile uploadFile, Long paraId) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperParagraph> allHeads = wordParser.getAllHeads();

        // 检验标题ID是否在文档标题的所属范围内
        checkParaIdInAllTitles(allHeads, paraId);
        int curTitleID = allHeads.get((int) (paraId - 1)).getParagraphID();
        // 判断是否存在后一个标题
        int nextTitleID = getNextTitleId(allHeads, paraId);
        System.out.println("标题所在段落ID区间：" + curTitleID + "  " + nextTitleID);

        List<SuperTable> tables = wordParser.getAllTables();
        if(tables.size() > 0){
            return tables.stream().filter(table -> {
                if (table.getParagraphBefore() != null && table.getParagraphAfter() != null){
                    if(table.getParagraphBefore().getParagraphID() > curTitleID && table.getParagraphAfter().getParagraphID() < nextTitleID){
                        return true;
                    }
                }else if(table.getParagraphBefore() != null && table.getParagraphBefore().getParagraphID() > curTitleID && table.getParagraphBefore().getParagraphID() < nextTitleID){
                    return true;
                }else if(table.getParagraphAfter() != null && table.getParagraphAfter().getParagraphID() > curTitleID && table.getParagraphAfter().getParagraphID() < nextTitleID){
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
        }
        return null;
    }

    private void checkParaIdInAllTitles(List<SuperParagraph> allHeads, Long paraId) {
        if(paraId > allHeads.size() || paraId < 1){
            throw new HttpBadRequestException("标题的ID"+ paraId +"不在文档标题的所属范围内，应保证在1~" + allHeads.size() + "之间");
        }
    }

    private int getNextTitleId(List<SuperParagraph> allHeads, Long paraId){
        // 判断是否存在后一个标题
        if (paraId >= allHeads.size()){
            return paraId.intValue();
        }else{
            return allHeads.get(Math.toIntExact(paraId)).getParagraphID();
        }
    }

    @Override
    public SuperParagraph getParaInfoByParaId(MultipartFile uploadFile, Long paraId) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
        checkParaIdInAllParas(paragraphs, paraId);
        return paragraphs.get((int) (paraId - 1));
    }

    private void checkParaIdInAllParas(List<SuperParagraph> paragraphs, Long paraId) {
        if(paraId > paragraphs.size() || paraId < 1){
            throw new HttpBadRequestException("段落ID"+ paraId +"不在文档段落的所属范围内，应保证在1~" + paragraphs.size() + "之间");
        }
    }

    @Override
    public SuperFontStyle getFontStyleByParaId(MultipartFile uploadFile, Long paraId) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
        checkParaIdInAllParas(paragraphs, paraId);
        SuperParagraph superParagraph = paragraphs.get((int) (paraId - 1));
        return StyleWrapper.wrapperFontStyle(superParagraph);
    }

    @Override
    public SuperParagraphStyle getParaStyleByParaId(MultipartFile uploadFile, Long paraId) throws IOException {
        WordParser wordParser = parseFile(uploadFile);
        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
        checkParaIdInAllParas(paragraphs, paraId);
        SuperParagraph superParagraph = paragraphs.get((int) (paraId - 1));
        return StyleWrapper.wrapperParaStyle(superParagraph);
    }


    private WordParser parseFile(MultipartFile uploadFile) throws IOException {
        String fileName = uploadFile.getOriginalFilename();
        WordParser wordParser = WordParserFactory.createWordParser();
        wordParser.parser(uploadFile, fileName);
        return wordParser;
    }
}
