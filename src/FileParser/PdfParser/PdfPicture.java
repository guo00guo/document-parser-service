package FileParser.PdfParser;


class PdfPicture {
    private String textBefore;
    private String textAfter;
    private PdfParagraph paragraphBefore;
    private PdfParagraph paragraphAfter;
    private double height;
    private double width;
    private String suggestFileExtension;
    private String base64Content;
    private int dxaGoal;
    private int dyaGoal;
    private int paragraphID;
    private String fileName;
    private int index;

    public String getTextBefore() {
        return textBefore;
    }

    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    public String getTextAfter() {
        return textAfter;
    }

    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }


    public void setHeight(double height) {
        this.height = height;
    }


    public void setWidth(double width) {

        this.width = width;
    }


    public String getSuggestFileExtension() {
        return suggestFileExtension;
    }

    public void setSuggestFileExtension(String suggestFileExtension) {
        this.suggestFileExtension = suggestFileExtension;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public int getDxaGoal() {
        return dxaGoal;
    }

    public void setDxaGoal(int dxaGoal) {
        this.dxaGoal = dxaGoal;
    }

    public int getDyaGoal() {
        return dyaGoal;
    }

    public void setDyaGoal(int dyaGoal) {
        this.dyaGoal = dyaGoal;
    }

    public int getPictureID() {
        return index;
    }

    public void setPictureID(int index) {
        this.index = index;
    }

    public PdfParagraph getParagraphBefore() {
        return paragraphBefore;
    }

    public void setParagraphBefore(PdfParagraph paragraphBefore) {
        this.paragraphBefore = paragraphBefore;
    }

    public PdfParagraph getParagraphAfter() {
        return paragraphAfter;
    }

    public void setParagraphAfter(PdfParagraph paragraphAfter) {
        this.paragraphAfter = paragraphAfter;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setParagraphID(int paragraphID) {
        this.paragraphID = paragraphID;
    }
}
