package com.mt;

public class RowModel {
    private String section;
    private String subsection;
    private String classification;
    private String document;
    private String title2;
    private String title3;
    private String title4;
    private String title5;
    private String title6;
    private String title7;
    private String title8;

    public RowModel(String section, String subsection, String classification, String document, String title2, String title3, String title4, String title5, String title6, String title7, String title8) {
        this.section = section;
        this.subsection = subsection;
        this.classification = classification;
        this.document = document;
        this.title2 = title2;
        this.title3 = title3;
        this.title4 = title4;
        this.title5 = title5;
        this.title6 = title6;
        this.title7 = title7;
        this.title8 = title8;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDocument() {
        return document;
    }

    public void setTitle1(String document) {
        this.document = document;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getTitle4() {
        return title4;
    }

    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    public String getTitle5() {
        return title5;
    }

    public void setTitle5(String title5) {
        this.title5 = title5;
    }

    public String getTitle6() {
        return title6;
    }

    public void setTitle6(String title6) {
        this.title6 = title6;
    }

    public String getTitle7() {
        return title7;
    }

    public void setTitle7(String title7) {
        this.title7 = title7;
    }

    public String getTitle8() {
        return title8;
    }

    public void setTitle8(String title8) {
        this.title8 = title8;
    }

    @Override
    public String toString() {
        return "RowModel{" +
                "section='" + section + '\'' +
                ", subsection='" + subsection + '\'' +
                ", classification='" + classification + '\'' +
                ", title1='" + document + '\'' +
                ", title2='" + title2 + '\'' +
                ", title3='" + title3 + '\'' +
                ", title4='" + title4 + '\'' +
                ", title5='" + title5 + '\'' +
                ", title6='" + title6 + '\'' +
                ", title7='" + title7 + '\'' +
                ", title8='" + title8 + '\'' +
                '}';
    }
}
