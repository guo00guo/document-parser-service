package com.mooctest.domainObject.DocxParser;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Lists;
import com.microsoft.schemas.vml.CTShape;
import com.mooctest.domainObject.SuperParagraph;
import com.mooctest.domainObject.SuperPicture;
import com.mooctest.domainObject.SuperTable;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class DocxParser {
    private String docPath = null;
    private File file;
    private InputStream stream = null;
    private XWPFDocument document = null;
    private List<DocxParagraph> docParagraphs = new ArrayList<DocxParagraph>();
    private List<DocxTable> docxTables = new ArrayList<DocxTable>();
    private List<DocxSuperPicture> docxPictures = new ArrayList<DocxSuperPicture>();
    private int paragraph_index = 0;
    private int table_index = 0;
    private int picture_index = 0;
    private String pre_type = "段落";
    private String asciiFontName = "";
    private String eastAsiaFontName = "";
    private List<String> pictureNames = new ArrayList<String>();
    private boolean errorWord = false;

    private void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public DocxParser(File file) {
        this.file = file;
        this.loadFile();
        if (!errorWord) {
            this.processDefaultValue();
            this.processContent();
        }
    }

    private void processDefaultValue() {
        XWPFDefaultRunStyle xwpfDefaultRunStyle = this.document.getStyles().getDefaultRunStyle();
//        if (xwpfDefaultRunStyle.getRPr().getRFonts().getEastAsia() != null) {
//            this.setEastAsiaFontName(xwpfDefaultRunStyle.getRPr().getRFonts().getEastAsia());
//        } else {
//            this.setEastAsiaFontName("");
//        }
//        if (xwpfDefaultRunStyle.getRPr().getRFonts().getAscii() != null) {
//            this.setAsciiFontName(xwpfDefaultRunStyle.getRPr().getRFonts().getAscii());
//        } else {
//            this.setAsciiFontName("");
//        }

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

    private void loadFile() {
        try {
            this.stream = new FileInputStream(this.file);
            this.document = new XWPFDocument(this.stream).getXWPFDocument();
        } catch (Exception e) {
            errorWord = true;
        }
    }


    /**
     * @param str ww
     * @return s
     */
    private static String unescapeJava(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    private int getLvl(XWPFParagraph paragraph) {
        XWPFStyles styles = this.document.getStyles();
        int lvl = 9;
        try {
            // 判断该段落是否设置了大纲级别
            if (paragraph.getCTP().isSetPPr()) {
                lvl = paragraph.getCTP().getPPr().getOutlineLvl().getVal().intValue();
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        try {
            //判断该段落的样式是否设置了大纲级别
            if (paragraph.getStyle() != null) {
                lvl = styles.getStyle(paragraph.getStyle()).getCTStyle().getPPr()
                        .getOutlineLvl().getVal().intValue();
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }
        try {
            //判断该段落的样式的基础样式是否设置了大纲级别
            lvl = styles
                    .getStyle(styles.getStyle(paragraph.getStyle()).getCTStyle().getBasedOn().getVal())
                    .getCTStyle().getPPr().getOutlineLvl().getVal().intValue();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return lvl;
    }

    private String processText(XWPFParagraph paragraph) {
//        return unescapeJava(paragraph.getParagraphText());
        if (null != paragraph.getNumLevelText()) {
            return paragraph.getNumLevelText() + paragraph.getNumFmt() + unescapeJava(paragraph.getParagraphText());
        } else {
            return unescapeJava(paragraph.getParagraphText());
        }
    }

    private String getFontFamily(XWPFRun xwpfRun) {
        return xwpfRun.getFontFamily() != null ? xwpfRun.getFontFamily() : this.getEastAsiaFontName();
    }

    private String getColor(XWPFRun xwpfRun) {
        return xwpfRun.getColor() != null ? xwpfRun.getColor() : "000000";
    }

    private void processTable(XWPFTable xwpfTable, int index) {
        DocxTable docxTable = new DocxTable();
        docxTable.setIndex(index);

        //设置表格前面的段落信息
        if (this.paragraph_index > 0) {
            docxTable.setParagraphBefore(this.docParagraphs.get(this.paragraph_index - 1));
            docxTable.setTextBefore(this.docParagraphs.get(this.paragraph_index - 1).getParagraphText());
        }

        for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
            List<List<DocxParagraph>> xtableRow = new ArrayList<>();
            for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
                List<DocxParagraph> paragraphsCell = new ArrayList<>();
//                int i = 0;
                for (XWPFParagraph xwpfParagraph : xwpfTableCell.getParagraphs()) {
                    paragraphsCell.add(this.processParagraph(xwpfParagraph, this.paragraph_index++));
//                    i += 1;
                }
                xtableRow.add(paragraphsCell);
            }
            docxTable.tableContent.add(xtableRow);
        }

        //设置表格后面的段落信息
        if (this.paragraph_index > 0) {
            docxTable.setParagraphAfter(this.docParagraphs.get(this.paragraph_index-1));
            docxTable.setTextAfter(this.docParagraphs.get(this.paragraph_index-1).getParagraphText());
        }
        this.docxTables.add(docxTable);
        this.setPre_type("表格");
    }

    private DocxParagraph processParagraph(XWPFParagraph paragraph, int index) {
        //解析段落信息
        DocxParagraph docxParagraph = new DocxParagraph();
        docxParagraph.setFirstLineIndent(paragraph.getFirstLineIndent());
        docxParagraph.setFontAlignment(paragraph.getFontAlignment());
        docxParagraph.setIndentFromLeft(paragraph.getIndentFromLeft());
        docxParagraph.setIndentFromRight(paragraph.getIndentFromRight());
        docxParagraph.setLvl(this.getLvl(paragraph));
        docxParagraph.setParagraphText(this.processText(paragraph));
        docxParagraph.setLineSpacing(paragraph.getSpacingLineRule().getValue());
        docxParagraph.setParagraphID(index);
        docxParagraph.setFontAlignment(paragraph.getFontAlignment());
        docxParagraph.setAlignment(paragraph.getAlignment());
        paragraph.getIndentFromRight();

        //解析字体格式等
        String fontName = "";
        String color = "";
        int fontSize = -1;
        boolean bold = false;
        boolean italic = false;
        boolean highlighted = false;
        boolean strike = false;
        UnderlinePatterns underline = UnderlinePatterns.NONE;
        List<XWPFRun> xwpfRuns = paragraph.getRuns();
        for (int i = 0; i < xwpfRuns.size(); i++) {
            XWPFRun xwpfRun = xwpfRuns.get(i);
//            fontSize = xwpfRun.getFontSize();
//            fontName = this.getFontFamily(xwpfRun);  // getFontFamily() ，仅当运行的run属性中存在字体系列时，它才会返回字体系列。否则它将返回null
//            color = this.getColor(xwpfRun);
//            bold = xwpfRun.isBold();
//            italic = xwpfRun.isItalic();
//            highlighted = xwpfRun.isHighlighted();
//            underline = xwpfRun.getUnderline();
//            strike = xwpfRun.isStrikeThrough();
//
//            if(index <  25){
//                System.out.println(index + "  " + i + "  " + xwpfRun.getText(0) + "  fontSize:"+ fontSize + "  fontName:"+ fontName + "  color:" + color
//                        + "  underLine:" + underline+ "  bold:" + bold + "  italic:"+ italic+ "  highlighted:"+ highlighted+ "  strike:"+ strike
//                        );
//            }

            if (i == 0) {
                fontSize = xwpfRun.getFontSize();
                fontName = this.getFontFamily(xwpfRun);
                color = this.getColor(xwpfRun);
                bold = xwpfRun.isBold();
                italic = xwpfRun.isItalic();
                highlighted = xwpfRun.isHighlighted();
                underline = xwpfRun.getUnderline();
                strike = xwpfRun.isStrikeThrough();
            } else {
                if (fontSize == -1 && fontName.equals("") && color.equals("")) break;
                if (fontSize != xwpfRun.getFontSize()) fontSize = -1;
                if (!fontName.equals(this.getFontFamily(xwpfRun))) fontName = "";
                if (!color.equals(this.getColor(xwpfRun))) color = "";
                if (bold != xwpfRun.isBold()) bold = false;
                if (italic != xwpfRun.isItalic()) italic = false;
                if (highlighted != xwpfRun.isHighlighted()) highlighted = false;
                if (underline != xwpfRun.getUnderline()) underline = UnderlinePatterns.NONE;
                if (strike != xwpfRun.isStrikeThrough()) strike = false;
            }
        }
        docxParagraph.setFontName(fontName);
        docxParagraph.setFontSize(fontSize);
        docxParagraph.setColor(color);
        docxParagraph.setBold(bold);
        docxParagraph.setItalic(italic);
        docxParagraph.setHighlighted(highlighted);
        docxParagraph.setUnderline(underline);
        docxParagraph.setStrike(strike);

        //加入对图片、 表格的后续段落设置
        this.docParagraphs.add(docxParagraph);
        if (this.getPre_type().equals("图片")) {// 如果之前的格式是图片，说明这个段落是表格前面的段落
            this.docxPictures.get(this.docxPictures.size() - 1).setParagraphAfter(docxParagraph);
            this.docxPictures.get(this.docxPictures.size() - 1).setTextAfter(docxParagraph.getParagraphText());
        }
//        else if (this.getPre_type().equals("表格")) {// 如果之前的格式是表格，说明这个段落是表格后面的散落
//            this.docxTables.get(this.docxTables.size() - 1).setParagraphAfter(docxParagraph);
//            this.docxTables.get(this.docxTables.size() - 1).setTextAfter(docxParagraph.getParagraphText());
//        }
        else{
            this.setPre_type("段落");
        }
        for (XWPFRun xwpfRun : paragraph.getRuns()) {
             //解析文本框内容，有一些文字是在文本框之内的，需要将文本框内的文字作为段落进一步解析。
            procesGroup(paragraph, xwpfRun);

            //以下是对段落中的图片进行直接解析。
            CTR ctr = xwpfRun.getCTR();
            XmlCursor c = ctr.newCursor();
            //这个就是拿到所有的子元素：
            c.selectPath("./*");
            while (c.toNextSelection()) {
                XmlObject o = c.getObject();
                //如果子元素是<w:drawing>这样的形式，使用CTDrawing保存图片
                if (o instanceof CTDrawing) { // 一般的图片信息，png，jpg等
                    CTDrawing drawing = (CTDrawing) o;
                    CTInline[] ctInlines = drawing.getInlineArray();
                    for (CTInline ctInline : ctInlines) {
                        CTGraphicalObject graphic = ctInline.getGraphic();
                        XmlCursor cursor = graphic.getGraphicData().newCursor();
                        cursor.selectPath("./*");
                        while (cursor.toNextSelection()) {
                            XmlObject xmlObject = cursor.getObject();
                            //如果子元素是<pic:pic>这样的形式
                            if (xmlObject instanceof CTPicture) {
                                CTPicture picture = (CTPicture) xmlObject;
                                //拿到元素的属性
                                this.processPicture(this.document.getPictureDataByID(picture.getBlipFill().getBlip().getEmbed()), this.paragraph_index);
                            }
                        }
                    }
                }
                if (o instanceof CTObject) { // visio图片等，emf
                    CTObject object = (CTObject) o;
                    XmlCursor w = object.newCursor();
                    w.selectPath("./*");
                    while (w.toNextSelection()) {
                        XmlObject xmlObject = w.getObject();
                        if (xmlObject instanceof CTShape) {
                            CTShape myshape = (CTShape) xmlObject;
                            Node node = myshape.getDomNode();
                            Node imageDataNode = getChildNode(node, "v:imagedata");
                            if (imageDataNode != null) {
                                Node blipNode = imageDataNode.getAttributes().getNamedItem("r:id");
                                if (blipNode != null) {
                                    this.processPicture(this.document.getPictureDataByID(blipNode.getNodeValue()), this.paragraph_index);
                                }
                            }
                        }
                    }
                }
            }
        }
        // 下面是找到所有的段落信息
        return docxParagraph;
    }

    private void procesGroup(XWPFParagraph paragraph, XWPFRun xwpfRun) {
        // 首先查看是否存在group的情况，如果存在，则表示存在将两幅图片、图片和文本框进行融合的情况。
        XmlObject[] groupObjects = xwpfRun.getCTR().selectPath("" +
                "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' " +
                "declare namespace wps='http://schemas.microsoft.com/office/word/2010/wordprocessingShape'" +
                "declare namespace v='urn:schemas-microsoft-com:vml' .//*/v:group");
        if (groupObjects.length > 0) { // 存在word中的组合，主要解决有一些文档将图片进行组合的情景
            for (XmlObject groupObject : groupObjects) {
                XmlCursor c = groupObject.newCursor();
                c.selectPath("./*");
                while (c.toNextSelection()) {
                    XmlObject curXmlObject = c.getObject(); //获取子元素
                    // 对图片进行处理，如果存在图片的话，则这个元组含有v:imagedata节点，
                    // 并且该节点必然含有r:id属性值。
                    Node imageDataNode = getChildNode(curXmlObject.getDomNode(), "v:imagedata");
                    if (imageDataNode != null) {
                        Node blipNode = imageDataNode.getAttributes().getNamedItem("r:id");
                        if (blipNode != null) {
                            this.processPicture(this.document.getPictureDataByID(blipNode.getNodeValue()), this.paragraph_index);
                        }
                    }
                    //解析是否存在文本框；
                    processTextBox(paragraph, curXmlObject);
                }
            }
        }
        else { // 查看是否存在文本框的情况。
            XmlObject[] textBoxObjects = xwpfRun.getCTR().selectPath("" +
                    "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' " +
                    "declare namespace wps='http://schemas.microsoft.com/office/word/2010/wordprocessingShape'" +
                    "  .//*/w:textbox");
            for (XmlObject textBoxObject : textBoxObjects) {
                processTextBox(paragraph, textBoxObject);
            }
        }
    }
    private void processTextBox(XWPFParagraph paragraph, XmlObject curObject){
        XmlObject[] textBoxObjects = curObject.selectPath("" +
                "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' " +
                "declare namespace wps='http://schemas.microsoft.com/office/word/2010/wordprocessingShape'" +
                "  .//*/w:txbxContent");
        for (XmlObject textBoxObject : textBoxObjects) {
            try {
                XmlObject[] paraObjects = textBoxObject.
                        selectChildren(
                                new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "p"));
                XWPFParagraph embeddedPara;
                for (XmlObject paraObject : paraObjects) {
                    embeddedPara = new XWPFParagraph(CTP.Factory.parse(paraObject.xmlText()), paragraph.getBody());
                    this.processParagraph(embeddedPara, this.paragraph_index++);
                }
            } catch (Exception e) {
            }
        }
    }

    private Node getChildNode(Node node, String nodeName) {
        if (!node.hasChildNodes()) {
            return null;
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (nodeName.equals(childNode.getNodeName())) {
                return childNode;
            }
            childNode = getChildNode(childNode, nodeName);
            if (childNode != null) {
                return childNode;
            }
        }
        return null;
    }

    private void processPicture(XWPFPictureData pictureData, int paragraphID) {
        if (this.pictureNames.contains(pictureData.toString())) return;
        DocxSuperPicture docxPicture = new DocxSuperPicture();
        docxPicture.setParagraphID(paragraphID);
        docxPicture.setIndex(this.picture_index++);
        docxPicture.setFileName(pictureData.getFileName());
        docxPicture.setSuggestFileExtension(pictureData.suggestFileExtension());
        docxPicture.setBase64Content(Base64.encodeBase64String(pictureData.getData()));
        this.setPre_type("图片");
//        if (paragraphID > 1) {
//            docxPicture.setParagraphBefore(this.docParagraphs.get(paragraphID-1));
//            docxPicture.setTextBefore(this.docParagraphs.get(paragraphID-1).getParagraphText());
//        }
        if (paragraphID > 1) {
            docxPicture.setParagraphBefore(this.docParagraphs.get(paragraphID - 2));
            docxPicture.setTextBefore(this.docParagraphs.get(paragraphID - 2).getParagraphText());
        }
        this.pictureNames.add(pictureData.toString());
//        System.out.println(pictureData.toString());
        this.docxPictures.add(docxPicture);
    }

    private void processContent() {
        if (null != this.document) {
            try {
                List<IBodyElement> iBodyElements = document.getBodyElements();
                for (IBodyElement elem : iBodyElements) {
                    // 处理段落
                    if (elem instanceof XWPFParagraph) {
                        this.processParagraph((XWPFParagraph) elem, this.paragraph_index++);
                    }
                    // 处理表格
                    else if (elem instanceof XWPFTable) {
                        this.processTable((XWPFTable) elem, this.table_index++);
                    }
                    // 处理其他
                    else {
                        System.out.println("other");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public List<SuperParagraph> getAllParagraphs() {
        List<SuperParagraph> contextList = Lists.newArrayList();
//        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
//        filter.getExcludes().add("XWPFParagraph");
        for (DocxParagraph docParagraph : this.docParagraphs) {
            contextList.add(docParagraph);
        }
        return contextList;
    }

    public List<SuperParagraph> getAllHeads() {
        List<SuperParagraph> contextList = Lists.newArrayList();
//        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
//        filter.getExcludes().add("XWPFParagraph");
        for (DocxParagraph docParagraph : this.docParagraphs) {
            if (docParagraph.getLvl() < 9) {
                contextList.add(docParagraph);
            }
        }
        return contextList;
    }

    public List<SuperTable> getAllTables() {
        List<SuperTable> contextList = Lists.newArrayList();
        for (DocxTable docxTable : this.docxTables) {
            contextList.add(docxTable);
        }
        return contextList;
    }

    public List<SuperPicture> getAllPictures() {
        List<SuperPicture> contextList = Lists.newArrayList();
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
//        filter.getExcludes().add("XWPFParagraph");
        for (DocxSuperPicture docxPicture : this.docxPictures) {
            contextList.add(docxPicture);
        }
        return contextList;
    }
}
