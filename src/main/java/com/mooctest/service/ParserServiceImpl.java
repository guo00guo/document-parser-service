package com.mooctest.service;

import com.google.gson.Gson;
import com.mooctest.data.StyleWrapper;
import com.mooctest.domainObject.DocParser.DocParagraph;
import com.mooctest.domainObject.DocParser.DocParser;
import com.mooctest.domainObject.DocParser.DocPicture;
import com.mooctest.domainObject.DocParser.DocTable;
import com.mooctest.domainObject.DocxParser.DocxParagraph;
import com.mooctest.domainObject.DocxParser.DocxParser;
import com.mooctest.domainObject.DocxParser.DocxPicture;
import com.mooctest.domainObject.DocxParser.DocxTable;
import com.mooctest.domainObject.PdfParser.PdfParagraph;
import com.mooctest.domainObject.PdfParser.PdfParser;
import com.mooctest.domainObject.PdfParser.PdfPicture;
import com.mooctest.domainObject.PdfParser.PdfTable;
import com.mooctest.domainObject.*;
import com.mooctest.exception.HttpBadRequestException;
import com.mooctest.factory.WordParserFactory;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author guochao
 * @date 2020-05-08 17:30
 */
@Service
@Slf4j
public class ParserServiceImpl implements ParserService {

//    @Override
//    public List<SuperParagraph> parserFile(String token){
//        WordParser wordParser = parseFile(uploadFile);
//
//        List<SuperParagraph> paragraphsPre = wordParser.getAllParagraphs();
//        int i = 0;
//        for (SuperParagraph paragraph : paragraphsPre) {
//            System.out.println(i + " " + paragraph.toString());
//            i++;
//        }
//
//        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
//        return paragraphs;
//    }

    @Override
    public String parserFile(MultipartFile uploadFile) throws IOException {
        String token = parseFileContent(uploadFile);
        return token;
    }

    @Override
    public List<SuperParagraph> getAllPara(String token){
        WordParser wordParser = getObjectFromJsonFile(token);
        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
        return paragraphs;
    }

    @Override
    public List<SuperPicture> getAllPicture(String token){
        WordParser wordParser = getObjectFromJsonFile(token);
        List<SuperPicture> allSuperPictures = wordParser.getAllPictures();
        return allSuperPictures;
    }

    @Override
    public List<SuperTable> getAllTable(String token){
        WordParser wordParser = getObjectFromJsonFile(token);
        List<SuperTable> allSuperTables = wordParser.getAllTables();
        return allSuperTables;
    }

    @Override
    public List<SuperParagraph> getAllTitle(String token){
        WordParser wordParser = getObjectFromJsonFile(token);
        if(token.startsWith("pdf")){
            throw new HttpBadRequestException("暂不支持pdf的标题获取！");
        }
//        List<SuperTitle> allHeads = wordParser.getAllHeads().stream().map(para -> StyleWrapper.wrapperTitle(para)).collect(Collectors.toList());
        List<SuperParagraph> allHeads = wordParser.getAllHeads();
        return allHeads;
    }

    @Override
    public List<SuperParagraph> getAllParaByTitleId(String token, Long paraId){
        WordParser wordParser = getObjectFromJsonFile(token);
        List<SuperParagraph> allHeads = wordParser.getAllHeads();
        // 检验标题ID是否在文档标题的所属范围内
        checkParaIdInAllTitles(allHeads, paraId);
        int curTitleID = allHeads.get((int) (paraId - 1)).getParagraphID();
        // 判断是否存在后一个标题
        int nextTitleID = getNextTitleId(wordParser, allHeads, paraId);
        System.out.println("标题所在段落ID区间：" + curTitleID + "  " + nextTitleID);

        // 判断是否为最后一个标题
        if(nextTitleID == wordParser.getAllParagraphs().size() - 1){
            return wordParser.getAllParagraphs().stream().filter(paragraph -> paragraph.getParagraphID() > curTitleID && paragraph.getParagraphID() <= nextTitleID).collect(Collectors.toList());

        }
        return wordParser.getAllParagraphs().stream().filter(paragraph -> paragraph.getParagraphID() > curTitleID && paragraph.getParagraphID() < nextTitleID).collect(Collectors.toList());
    }

    @Override
    public List<SuperPicture> getAllPictureByTitleId(String token, Long paraId){
        WordParser wordParser = getObjectFromJsonFile(token);
        List<SuperParagraph> allHeads = wordParser.getAllHeads();
        // 检验标题ID是否在文档标题的所属范围内
        checkParaIdInAllTitles(allHeads, paraId);
        int curTitleID = allHeads.get((int) (paraId - 1)).getParagraphID();
        // 判断是否存在后一个标题
        int nextTitleID = getNextTitleId(wordParser, allHeads, paraId);
        System.out.println("标题所在段落ID区间：" + curTitleID + "  " + nextTitleID);

        // 判断是否为最后一个标题
        if(nextTitleID == wordParser.getAllParagraphs().size() - 1){
            return wordParser.getAllPictures().stream().filter(paragraph -> paragraph.getParagraphID() > curTitleID && paragraph.getParagraphID() <= nextTitleID).collect(Collectors.toList());

        }
        return wordParser.getAllPictures().stream().filter(picture -> picture.getParagraphID() > curTitleID && picture.getParagraphID() < nextTitleID).collect(Collectors.toList());
    }

    @Override
    public List<SuperTable> getAllTableByTitleId(String token, Long paraId){
        WordParser wordParser = getObjectFromJsonFile(token);
        List<SuperParagraph> allHeads = wordParser.getAllHeads();

        // 检验标题ID是否在文档标题的所属范围内
        checkParaIdInAllTitles(allHeads, paraId);
        int curTitleID = allHeads.get((int) (paraId - 1)).getParagraphID();
        // 判断是否存在后一个标题
        int nextTitleID = getNextTitleId(wordParser, allHeads, paraId);
        System.out.println("标题所在段落ID区间：" + curTitleID + "  " + nextTitleID);

        List<SuperTable> tables = wordParser.getAllTables();
        if(tables.size() > 0){
            // 判断是否为最后一个标题
            if(nextTitleID == wordParser.getAllParagraphs().size() - 1){
                return tables.stream().filter(table -> {
                    if (table.getParagraphBefore() != null && table.getParagraphAfter() != null){
                        if(table.getParagraphBefore().getParagraphID() > curTitleID && table.getParagraphAfter().getParagraphID() <= nextTitleID){
                            return true;
                        }
                    }else if(table.getParagraphBefore() != null && table.getParagraphBefore().getParagraphID() > curTitleID && table.getParagraphBefore().getParagraphID() <= nextTitleID){
                        return true;
                    }else if(table.getParagraphAfter() != null && table.getParagraphAfter().getParagraphID() > curTitleID && table.getParagraphAfter().getParagraphID() <= nextTitleID){
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
            }else{
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

        }
        return null;
    }

    private void checkParaIdInAllTitles(List<SuperParagraph> allHeads, Long paraId) {
        if(paraId > allHeads.size() || paraId < 1){
            throw new HttpBadRequestException("标题的ID"+ paraId +"不在文档标题的所属范围内，应保证在1~" + allHeads.size() + "之间");
        }
    }

    private int getNextTitleId(WordParser wordParser, List<SuperParagraph> allHeads, Long paraId){
        // 判断是否存在后一个标题
        if (paraId >= allHeads.size()){
            // 已经是最后一个标题，则返回最后一个段落的id
            return wordParser.getAllParagraphs().size() - 1;
        }else{
            return allHeads.get(Math.toIntExact(paraId)).getParagraphID();
        }
    }

    @Override
    public SuperParagraph getParaInfoByParaId(String token, Long paraId){
        WordParser wordParser = getObjectFromJsonFile(token);
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
    public SuperFontStyle getFontStyleByParaId(String token, Long paraId){
        WordParser wordParser = getObjectFromJsonFile(token);
        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
        checkParaIdInAllParas(paragraphs, paraId);
        SuperParagraph superParagraph = paragraphs.get((int) (paraId - 1));
        return StyleWrapper.wrapperFontStyle(superParagraph);
    }

    @Override
    public SuperParagraphStyle getParaStyleByParaId(String token, Long paraId){
        WordParser wordParser = getObjectFromJsonFile(token);
        List<SuperParagraph> paragraphs = wordParser.getAllParagraphs();
        checkParaIdInAllParas(paragraphs, paraId);
        SuperParagraph superParagraph = paragraphs.get((int) (paraId - 1));
        return StyleWrapper.wrapperParaStyle(superParagraph);
    }


//    private WordParser parseFile(MultipartFile uploadFile) throws IOException {
//        String fileName = uploadFile.getOriginalFilename();
//        WordParser wordParser = WordParserFactory.createWordParser();
//        wordParser.parser(uploadFile, fileName);
//        return wordParser;
//    }

    private String parseFileContent(MultipartFile uploadFile) throws IOException {
        String fileName = uploadFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        WordParser wordParser = WordParserFactory.createWordParser();
        wordParser.parser(uploadFile, fileName);
        String absolutePath = System.getProperty("user.dir");
        String path = absolutePath + "/fileTemp/";

        String fileLowerName = fileName.toLowerCase();
        String fileType = fileLowerName.substring(fileLowerName.lastIndexOf(".") + 1, fileLowerName.length());
        String token = fileType + "-" + System.currentTimeMillis() + "-" + uuid;
        String filePath = path + token + ".json";
        boolean createFileFlag = createJsonFile(wordParser, filePath);

        if(createFileFlag){
            return token;
        }else{
            throw new HttpBadRequestException("文档解析失败！");
        }

    }

    /**
     * 将JSON数据格式化并保存到文件中
     * @param jsonData 需要输出的json数
     * @param filePath 输出的文件地址
     * @return
     */
    private static boolean createJsonFile(Object jsonData, String filePath) {
        Gson gson = new Gson();
        String content = gson.toJson(jsonData);
        // 标记文件生成是否成功
        boolean flag = true;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(filePath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(content);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    private WordParser getObjectFromJsonFile(String token) {
        String absolutePath = System.getProperty("user.dir");
        String path = absolutePath + "/fileTemp/";
        String filePath = path + token + ".json";
        String jsonData = readJsonData(filePath);
        Map<String, Object> map = new HashMap<>();

        map.put("docParser", DocParser.class);
        map.put("docxParser", DocxParser.class);
        map.put("pdfParser", PdfParser.class);

        map.put("docxParagraphs", DocxParagraph.class);
        map.put("docxTables", DocxTable.class);
        map.put("docxTableContent", DocxParagraph.class);
        map.put("docxPictures", DocxPicture.class);

        map.put("docParagraphs", DocParagraph.class);
        map.put("docTables", DocTable.class);
        map.put("docPictures", DocPicture.class);
        map.put("docTableContent", DocParagraph.class);

        map.put("pdfParagraphs", PdfParagraph.class);
        map.put("pdfTables", PdfTable.class);
        map.put("pdfPictures", PdfPicture.class);
        map.put("pdfTableContent", PdfParagraph.class);

        WordParser wordParser = (WordParser) JSONObject.toBean(JSONObject.fromObject(jsonData), WordParser.class, map);
        return wordParser;
    }

    private static String readJsonData(String pactFile){
        // 读取文件数据
        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile);
        if (!myFile.exists()) {
            System.err.println("Can't Find " + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return strbuffer.toString();
    }
}

