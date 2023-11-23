package com.example.restaurant.convertor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class stringToTimestamp implements Converter<String, Timestamp> {

    @Override
    public Timestamp convert(String source) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyy-MM-dd'T'hh:mm:ss");
            return new Timestamp(formatter.parse(source).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}