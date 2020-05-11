package com.mooctest.domainObject.DocParser;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.mooctest.domainObject.SuperParagraph;
import com.mooctest.domainObject.SuperPicture;
import com.mooctest.domainObject.SuperTable;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DocParser {
    private File file;
    private InputStream stream = null;
    private HWPFDocument document = null;
    private List<DocParagraph> docParagraphs = new ArrayList<DocParagraph>();
    private List<DocTable> docTables = new ArrayList<DocTable>();
    private List<DocSuperPicture> docPictures = new ArrayList<DocSuperPicture>();

    public DocParser(File file) {
        this.file = file;
        this.loadFile();
        this.processContent();
        this.processPicture();
    }

    protected void finalize() {
        if (null != this.document) {
            try {
                this.document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null != this.stream) {
            try {
                this.stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFile() {
        try {
            this.stream = new FileInputStream(this.file);
            this.document = new HWPFDocument(this.stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DocParagraph processParagraph(Paragraph paragraph, int index) {
        //解析段落信息
        DocParagraph docParagraph = new DocParagraph();
        ParagraphProperties paragraphProperties = paragraph.getProps();
        docParagraph.setFirstLineIndent(paragraph.getFirstLineIndent());
        docParagraph.setFontAlignment(paragraph.getFontAlignment());
        docParagraph.setIndentFromLeft(paragraph.getIndentFromLeft());
        docParagraph.setIndentFromRight(paragraph.getIndentFromRight());
        docParagraph.setLvl(paragraph.getLvl());
        docParagraph.setLlvl(paragraph.getIlvl());
        docParagraph.setLinfo(paragraph.getIlfo());
        docParagraph.setParagraphText(CharMatcher.whitespace().removeFrom(WordExtractor.stripFields(paragraph.text())).trim());
        docParagraph.setParagraph(paragraph);
        docParagraph.setInTable(paragraph.isInTable());
        docParagraph.setLineSpacing(paragraphProperties.getLineSpacing().toString());
        docParagraph.setTableRowEnd(paragraph.isTableRowEnd());
        docParagraph.setJustification(paragraph.getJustification());
        docParagraph.setParagraphID(index);
        int fontSize = -1;
        String fontName = "";
        boolean isBold = false;
        boolean isItalic = false;
        CharacterRun run = null;
        for (int i = 0; i < paragraph.numCharacterRuns(); i++) {
            run = paragraph.getCharacterRun(i);
            if (i == 0) {
                fontSize = run.getFontSize();
                fontName = run.getFontName();
                isBold = run.isBold();
                isItalic = run.isItalic();
            } else {
                if (fontSize != run.getFontSize()) {
                    fontSize = -1;
                }
                if (!fontName.equals(run.getFontName())) {
                    fontName = "";
                }
                if (isBold != run.isBold()) {
                    isBold = false;
                }
                if (isItalic != run.isItalic()) {
                    isItalic = false;
                }
            }
        }
        docParagraph.setFontSize(fontSize);
        docParagraph.setFontName(fontName);
        docParagraph.setBold(isBold);
        docParagraph.setItalic(isItalic);
        return docParagraph;
    }

    private DocTable processTable(Range range, int start, int end) {
        DocTable docTable = new DocTable();
        if (start != 0) {
            docTable.setParagraphBefore(this.docParagraphs.get(this.docParagraphs.size() - 1));
            docTable.setTextBefore(docTable.getParagraphBefore().getParagraphText());
        }
        IntStream.range(start, end).forEachOrdered(index -> {
            Paragraph paragraph = range.getParagraph(index);
            DocParagraph docParagraph = this.processParagraph(paragraph, index);
            docTable.docParagraphs.add(docParagraph);
        });
        if (end < range.numParagraphs()) {
            docTable.setParagraphAfter(this.processParagraph(range.getParagraph(end), end));
            docTable.setTextAfter(docTable.getParagraphAfter().getParagraphText());
        }
        return docTable;
    }


    private void processPicture() {


        PicturesTable picturesTable = this.document.getPicturesTable();
        int length = document.characterLength();
        List<String> pictureTextBefore = Lists.newArrayList();
        List<String> pictureTextAfter = Lists.newArrayList();


        List<Picture> pictures = picturesTable.getAllPictures();
        try {
            for (int i = 0; i < length; i++) {

                Range range = new Range(i, i + 1, document);
                CharacterRun characterRun = range.getCharacterRun(0);
                if (picturesTable.hasPicture(characterRun)) {
                    Range preRange;
                    if (i - 1000 >= 0) {
                        preRange = new Range(i - 1000, i, document);
                    } else {
                        preRange = new Range(0, i, document);
                    }
                    Range nextRange;
                    if (i + 1000 < length) {
                        nextRange = new Range(i + 4, i + 1000, document);
                    } else {
                        nextRange = new Range(i + 4, length - 1, document);
                    }
                    Paragraph preparagraph1, preparagraph2, nextparagraph1, nextparagraph2;
                    if (preRange.numParagraphs() >= 3) {
                        preparagraph1 = preRange.getParagraph(preRange.numParagraphs() - 3);
                        preparagraph2 = preRange.getParagraph(preRange.numParagraphs() - 2);
                    } else {
                        preparagraph1 = preRange.getParagraph(0);
                        preparagraph2 = preRange.getParagraph(0);
                    }
                    if (nextRange.numParagraphs() > 1) {
                        nextparagraph1 = nextRange.getParagraph(0);
                        nextparagraph2 = nextRange.getParagraph(1);
                    } else {
                        nextparagraph1 = nextRange.getParagraph(0);
                        nextparagraph2 = nextRange.getParagraph(0);
                    }
                    String textBefore = preparagraph1.text() + preparagraph2.text();
                    pictureTextBefore.add(textBefore);
                    String textafter = nextparagraph1.text() + nextparagraph2.text();
                    pictureTextAfter.add(textafter);


                }

            }

            int pictureNO = 0;
            for (Picture picture : pictures) {
                DocSuperPicture docPicture = new DocSuperPicture();
                docPicture.setHeight(picture.getHeight());
                docPicture.setWidth(picture.getWidth());
                docPicture.setTextBefore(CharMatcher.whitespace().removeFrom(WordExtractor.stripFields(pictureTextBefore.get(pictureNO))).trim());
                docPicture.setTextAfter(CharMatcher.whitespace().removeFrom(WordExtractor.stripFields(pictureTextAfter.get(pictureNO))).trim());
                docPicture.setSuggestFileExtension(picture.suggestFileExtension());
                String base64 = Base64.encodeBase64String(picture.getContent());
                docPicture.setBase64Content(base64);
                this.docPictures.add(docPicture);
                pictureNO += 1;
            }
        } catch (Exception e) {
            // 捕捉到异常，
            e.printStackTrace();
        }
    }


//    private void processPicture() {
//
//        PicturesTable picturesTable = this.document.getPicturesTable();
//
//        List<Picture> pictures = picturesTable.getAllPictures();
//        int index = 1;
//        for (Picture picture : pictures) {
////            picture.
//            DocPicture docPicture = new DocPicture();
////            docPicture.setTextBefore();
//            docPicture.setHeight(picture.getHeight());
//            docPicture.setWidth(picture.getWidth());
//            docPicture.setSuggestFileExtension(picture.suggestFileExtension());
//            String base64 = Base64.encodeBase64String(picture.getContent());
//            docPicture.setBase64Content(base64);
//            this.docPictures.add(docPicture);
//
//        }
//    }

    private void processContent() {
        if (null != this.document) {
            try {
                Range range = this.document.getRange();
                int index = 0;
                int number = range.numParagraphs();
                while (number > index) {
                    Paragraph paragraph = range.getParagraph(index);
                    if (paragraph.isInTable()) {
                        int start = index;
                        int end = start + 1;
                        while (true) {
                            if (end >= number) break;
                            paragraph = range.getParagraph(end);
                            if (!paragraph.isInTable()) break;
                            end += 1;
                        }
                        docTables.add(this.processTable(range, start, end));
                        index = end;
                    } else {
                        this.docParagraphs.add(this.processParagraph(paragraph, index));
                        index += 1;
                    }
                }
            } catch (Exception e) {
                // 捕捉到异常，
                e.printStackTrace();
            }
        }
    }

    public List<SuperTable> getAllTables() {
        List<SuperTable> contextList = Lists.newArrayList();
        for (DocTable docTable : this.docTables) {
            contextList.add(docTable);
        }
        return contextList;
    }


    public List<SuperParagraph> getAllParagraphs() {
        List<SuperParagraph> contextList = Lists.newArrayList();
//        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
//        filter.getExcludes().add("paragraph");
        for (DocParagraph docParagraph : this.docParagraphs) {
            contextList.add(docParagraph);
        }
        return contextList;
    }

    public List<SuperParagraph> getAllHeads() {
        List<SuperParagraph> headList = Lists.newArrayList();
//        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
//        filter.getExcludes().add("paragraph");
        for (DocParagraph docParagraph : this.docParagraphs) {
            if (docParagraph.getLvl() < 9) {
                headList.add(docParagraph);
            }
        }
        return headList;
    }

    public List<SuperPicture> getAllPictures() {
        List<SuperPicture> superPictureList = Lists.newArrayList();
//        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
//        filter.getExcludes().add("paragraph");
//        for (DocPicture docParagraph : this.docPictures) {
//            pictureList.add(JSON.toJSONString(docParagraph, filter));
//        }
        for (DocSuperPicture docParagraph : this.docPictures) {
            superPictureList.add(docParagraph);
        }
        return superPictureList;
    }
}
