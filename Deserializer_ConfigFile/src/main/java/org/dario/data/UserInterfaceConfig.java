package org.dario.data;

public class UserInterfaceConfig {


    private String titleColor;
    private String titleText;
    private short titleFontSize;
    private short footerFontSize;

    @Override
    public String toString() {
        return "UserInterfaceConfig{" +
                "titleColor='" + titleColor + '\'' +
                ", titleText='" + titleText + '\'' +
                ", titleFontSize=" + titleFontSize +
                ", footerFontSize=" + footerFontSize +
                '}';
    }

    public String getTitleColor() {
        return titleColor;
    }

    public String getTitleText() {
        return titleText;
    }

    public short getTitleFontSize() {
        return titleFontSize;
    }

    public short getFooterFontSize() {
        return footerFontSize;
    }
}
