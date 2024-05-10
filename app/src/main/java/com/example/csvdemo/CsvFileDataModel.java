package com.example.csvdemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CsvFileDataModel {

    String firstName, middleName, lastName, age;

    public CsvFileDataModel(String firstName, String middleName, String lastName, String age) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getCsvRowLineData() {

        String data = firstName + "," + middleName + "," + lastName + "," + age;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        String time = sdf.format(new Date());

        return data + "," + time + "\n";
    }
}
