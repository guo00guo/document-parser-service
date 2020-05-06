package FileParser;

import FileParser.DocParser.DocParser;
import FileParser.DocxParser.DocxParser;
import FileParser.PdfParser.PdfParser;

import java.util.List;

public class WordParser {
    private String docPath = null;
    private DocParser docParser;
    private DocxParser docxParser;
    private PdfParser pdfParser;
    private String ext = null;
    private String fileType = "";

    private void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getDocPath() {
        return this.docPath;
    }

    public String getExt() {
        return this.ext;
    }

    private void setExt(String ext) {
        this.ext = ext;
    }

    public String getFileType() {
        return this.fileType;
    }

    private void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean parser(String docPath) {
        this.setDocPath(docPath);
        String path = docPath.toLowerCase();
        if (path.endsWith(".doc")) {
            try {
                docParser = new DocParser(this.docPath);
                this.setExt(".doc");
                this.setFileType(".doc");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (path.endsWith(".wps")) {
            try {
                docParser = new DocParser(this.docPath);
                this.setExt(".wps");
                this.setFileType(".doc");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (path.endsWith(".docx")) {
            try {
                docxParser = new DocxParser(this.docPath);
                this.setExt(".docx");
                this.setFileType(".docx");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (path.endsWith(".pdf")) {
            try {
                pdfParser = new PdfParser((this.docPath));
                this.setExt(".pdf");
                this.setFileType(".pdf");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public List<String> getAllParagraphs() {
        return this.ext == null ? null : this.fileType.equals(".doc") ? this.docParser.getAllParagraphs() : this.fileType.equals(".docx") ?
                this.docxParser.getAllParagraphs() : this.pdfParser.getAllParagraphs();
    }

    public List<String> getaAllTables() {
        return this.ext == null ? null : this.fileType.equals(".doc") ? this.docParser.getAllTables() : this.fileType.equals(".docx") ?
                this.docxParser.getAllTables() : this.pdfParser.getAllTables();
    }

    public List<String> getAllHeads() {
        return this.ext == null ? null : this.fileType.equals(".doc") ? this.docParser.getAllHeads() : this.docxParser.getAllHeads();
    }

    public List<String> getAllPictures() {
        return this.ext == null ? null : this.fileType.equals(".doc") ? this.docParser.getAllPictures() : this.fileType.equals(".docx") ?
                this.docxParser.getAllPictures() : this.pdfParser.getAllPictures();
    }

    public static void main(String[] args) {
        WordParser wordParser = new WordParser();
        wordParser.parser("/Users/guochao/Desktop/documentParserService/wordParserWithPOI-V4.1.2/testData/文档解析模块V0.3.docx");
        List<String> paragraphs = wordParser.getAllParagraphs();
        int i = 0;
        for (String paragraph : paragraphs) {
            System.out.println(i + "  paragraph----->" + paragraph);
            i++;
        }
    }
}
