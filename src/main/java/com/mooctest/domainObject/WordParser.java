package com.mooctest.domainObject;

import com.mooctest.domainObject.DocParser.DocParser;
import com.mooctest.domainObject.DocxParser.DocxParser;
import com.mooctest.domainObject.PdfParser.PdfParser;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Data
public class WordParser {
    private File file;
    private DocParser docParser;
    private DocxParser docxParser;
    private PdfParser pdfParser;
    private String ext = null;
    private String fileType = "";

    public boolean parser(MultipartFile uploadFile, String fileName) throws IOException {
        String path = "/Users/guochao/Desktop/documentParserService/wordParserWithPOI-V4.1.2/src/main/java/resources/fileTemp/";
        File destFile = new File(path, fileName);
        // 将MultipartFile存到临时文件中
        uploadFile.transferTo(destFile);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(destFile));
        bufferedReader.close();

        this.setFile(destFile);
        String fileLowerName = fileName.toLowerCase();
        if (fileLowerName.endsWith(".doc")) {
            try {
                docParser = new DocParser(this.getFile());
                this.setExt(".doc");
                this.setFileType(".doc");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        else if (fileLowerName.endsWith(".docx")) {
            try {
                docxParser = new DocxParser(this.getFile());
                this.setExt(".docx");
                this.setFileType(".docx");
                deleteFile(destFile);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                deleteFile(destFile);
                return false;
            }
        }
//        else if (fileLowerName.endsWith(".wps")) {
//            try {
//                docParser = new DocParser();
//                this.setExt(".wps");
//                this.setFileType(".doc");
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//        else if (fileLowerName.endsWith(".pdf")) {
//            try {
//                pdfParser = new PdfParser();
//                this.setExt(".pdf");
//                this.setFileType(".pdf");
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
        return false;
    }

    private void deleteFile(File file){
        // 操作完上的文件 需要删除在根目录下生成的文件
        if (!file.delete()){
            System.out.println("删除失败");
        }
    }

    public List<SuperParagraph> getAllParagraphs() {
        return this.ext == null ? null : this.fileType.equals(".doc") ? this.docParser.getAllParagraphs() : this.fileType.equals(".docx") ?
                this.docxParser.getAllParagraphs() : this.pdfParser.getAllParagraphs();
    }

    public List<SuperTable> getAllTables() {
        return this.ext == null ? null : this.fileType.equals(".doc") ? this.docParser.getAllTables() : this.fileType.equals(".docx") ?
                this.docxParser.getAllTables() : this.pdfParser.getAllTables();
    }

    public List<SuperParagraph> getAllHeads() {
        return this.ext == null ? null : this.fileType.equals(".doc") ? this.docParser.getAllHeads() : this.docxParser.getAllHeads();
    }

    public List<SuperPicture> getAllPictures() {
        return this.ext == null ? null : this.fileType.equals(".doc") ? this.docParser.getAllPictures() : this.fileType.equals(".docx") ?
                this.docxParser.getAllPictures() : this.pdfParser.getAllPictures();
    }
}