package com.example.exampleapplication.pojo;

import java.util.regex.Pattern;

/**
 * Created by Ayush on 07/06/20.
 */
public enum CardInfoEnum {
    MASTERCARD(Pattern.compile("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)"),
            "MASTERCARD", 16),
    AMEX(Pattern.compile("^3[47][0-9]{0,13}$"), "AMEX", 15);


    private Pattern patternS;
    private String cardType;
    private int maxLength;

    CardInfoEnum(Pattern patternS,
                 String cardType,
                 int maxLength) {
        this.cardType = cardType;
        this.maxLength = maxLength;
        this.patternS = patternS;
    }
}

