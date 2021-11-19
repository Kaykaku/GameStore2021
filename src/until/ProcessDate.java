/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import com.jfoenix.controls.JFXDatePicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import javafx.util.StringConverter;

/**
 *
 * @author Admin
 */
public class ProcessDate {

    static SimpleDateFormat formater = new SimpleDateFormat();
    public static String date_patern = "dd/MM/yyyy";

    public static Date toDate(String date) {
        try {
            formater.applyPattern(date_patern);
            return formater.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    public static String toString(Date date) {
        formater.applyPattern(date_patern);
        return formater.format(date);
    }
    public static String toString(Date date,String patern) {
        formater.applyPattern(patern);
        return formater.format(date);
    }

    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    public static void converter(JFXDatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ProcessDate.date_patern);

            {
                datePicker.setPromptText(ProcessDate.date_patern);
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return ProcessDate.toString(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    public static Date toDate(LocalDate localDate) {
        localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());      
        return date;
    }

    public static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static Date now() {
        return new Date();
    }
}
