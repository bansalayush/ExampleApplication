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

    public static CardText getFormattedCardTet(CardInfo cardType, String unformattedString) {
        StringBuilder textWithoutPlaceholder = new StringBuilder(unformattedString.replace("X", ""));
        String stringWithoutPlaceholder = textWithoutPlaceholder.toString();
        int lastDigitPointer = textWithoutPlaceholder.length();
        int diff = cardType.getMaxLength() - textWithoutPlaceholder.length();
        for (int i = 0; i < diff; i++)
            textWithoutPlaceholder.append("X");
        String formattedString = textWithoutPlaceholder.toString()
                .replaceFirst(cardType.getFormattingPattern(), cardType.getPlacementPattern());
        return new CardText(lastDigitPointer, cardType.getCardType(), formattedString, stringWithoutPlaceholder);

    }
}
