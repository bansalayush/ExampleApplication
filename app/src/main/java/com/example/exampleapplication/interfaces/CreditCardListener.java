package com.example.exampleapplication.interfaces;

import com.example.exampleapplication.pojo.CardInfo;

public interface CreditCardListener {
    void getCardType(CardInfo cardInfo);

    void getCardNumber(String cardNumber);

    void isValid(boolean isValid);
}