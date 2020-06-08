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

    public CardInfo(Pattern patternS, String cardType, int maxLength) {
        this.patternS = patternS;
        this.cardType = cardType;
        this.maxLength = maxLength;
    }

    public static CardInfo build(String pattern, String name, int maxLength) {
        return new CardInfo(Pattern.compile(pattern),
                name, maxLength);
    }

    public static CardInfo defaultCard() {
        return new CardInfo(Pattern.compile("^[0-9]"), UNKNOWN, 16);
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
