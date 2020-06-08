package com.example.exampleapplication.pojo;

import java.util.regex.Pattern;

import static com.example.exampleapplication.constants.Statics.UNKNOWN;

/**
 * Created by Ayush on 07/06/20.
 */
public class CardInfo {
    private Pattern patternS;
    private String cardType;
    private int maxLength;
    private String formattingPattern;
    private String placementPattern;

    public String getPlacementPattern() {
        return placementPattern;
    }

    public void setPlacementPattern(String placementPattern) {
        this.placementPattern = placementPattern;
    }

    public String getFormattingPattern() {
        return formattingPattern;
    }

    public void setFormattingPattern(String formattingPattern) {
        this.formattingPattern = formattingPattern;
    }

    public Pattern getPatternS() {
        return patternS;
    }

    public void setPatternS(Pattern patternS) {
        this.patternS = patternS;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public CardInfo(Pattern patternS, String cardType,
                    int maxLength, String formattingPattern, String placementPattern) {
        this.patternS = patternS;
        this.cardType = cardType;
        this.maxLength = maxLength;
        this.formattingPattern = formattingPattern;
        this.placementPattern = placementPattern;
    }

    public static CardInfo build(String pattern, String name,
                                 int maxLength, String formattingPattern,
                                 String placementPattern) {
        return new CardInfo(Pattern.compile(pattern),
                name, maxLength, formattingPattern, placementPattern);
    }

    public static CardInfo defaultCard() {
        return new CardInfo(Pattern.compile("^[0-9]"), UNKNOWN,
                16, "^([0-9X]{4})?([0-9X]{4})?([0-9X]{4})?([0-9X]{4})$",
                "$1 $2 $3 $4");
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "patternS=" + patternS +
                ", cardType='" + cardType + '\'' +
                ", maxLength=" + maxLength +
                '}';
    }
}
