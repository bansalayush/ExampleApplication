package com.example.exampleapplication.extraclasses;

import com.example.exampleapplication.pojo.CardInfo;
import com.example.exampleapplication.pojo.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.exampleapplication.constants.Statics.AMEX;
import static com.example.exampleapplication.constants.Statics.DINER;
import static com.example.exampleapplication.constants.Statics.MASTER;

/**
 * Created by Ayush on 2019-12-01.
 */
public class DataSource {
    public static List<Task> createTasksList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Take out the trash", true, 3));
        tasks.add(new Task("Walk the dog", false, 2));
        tasks.add(new Task("Make my bed", true, 1));
        tasks.add(new Task("Unload the dishwasher", false, 0));
        tasks.add(new Task("Make dinner", true, 5));
        return tasks;
    }

    public static List<CardInfo> getCardInfoList() {
        List<CardInfo> cardInfo = new ArrayList<>();
        CardInfo mastercard = CardInfo.build("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)",
                MASTER, 19);
        CardInfo amex = CardInfo.build("^3[47][0-9]{0,13}$", AMEX, 17);
        CardInfo diners = CardInfo.build("^3(?:0[0-5]|[68][0-9])[0-9]{0,11}$", DINER, 16);

        cardInfo.add(mastercard);
        cardInfo.add(amex);
        cardInfo.add(diners);

        return cardInfo;
    }
}
