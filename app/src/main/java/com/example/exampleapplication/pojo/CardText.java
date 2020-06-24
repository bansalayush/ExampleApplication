package com.example.exampleapplication.pojo;

/**
 * Created by Ayush on 08/06/20.
 */
public class CardText {

    private int lastDigitPointer;
    private String cardType;
    private String formattedString;
    private String numberString;

    public String getNumberString() {
        return numberString;
    }

    public void setNumberString(String numberString) {
        this.numberString = numberString;
    }

    public int getLastDigitPointer() {
        return lastDigitPointer;
    }

    public void setLastDigitPointer(int lastDigitPointer) {
        this.lastDigitPointer = lastDigitPointer;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getFormattedString() {
        return formattedString;
    }

    public void setFormattedString(String formattedString) {
        this.formattedString = formattedString;
    }

    public CardText(int lastDigitPointer, String cardType,
                    String formattedString, String numberString) {
        this.lastDigitPointer = lastDigitPointer;
        this.cardType = cardType;
        this.formattedString = formattedString;
        this.numberString = numberString;
    }

    public static CardText getFormattedCardTet(CardInfo cardType, String s) {
        StringBuilder textWithoutPlaceholder = new StringBuilder(s.replace("X", ""));
        int lastDigitPointer = textWithoutPlaceholder.length();//with spaces to maintain correct entering order

        //removing spaces
        StringBuilder stringWithoutPlaceholderAndSpaces = new StringBuilder(textWithoutPlaceholder
                .toString().replace(" ", ""));

        //don't edit this string
        String numberString = stringWithoutPlaceholderAndSpaces.toString();

        //adding placeholders
        int diff = cardType.getMaxLength() - stringWithoutPlaceholderAndSpaces.length();
        for (int i = 0; i < diff; i++)
            stringWithoutPlaceholderAndSpaces.append("X");

        //formatting string on the basis of regex
        String formattedString = stringWithoutPlaceholderAndSpaces.toString()
                .replaceFirst(cardType.getFormattingPattern(), cardType.getPlacementPattern());

        return new CardText(lastDigitPointer, cardType.getCardType(),
                formattedString, numberString);

    }
}
