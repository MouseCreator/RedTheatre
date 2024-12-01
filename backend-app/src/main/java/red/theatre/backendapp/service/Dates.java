package red.theatre.backendapp.service;

import org.springframework.stereotype.Component;
import red.theatre.backendapp.exception.DateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Dates {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String dateToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(FORMATTER);
    }

    public LocalDateTime stringToDate(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeString, FORMATTER);
        } catch (Exception e) {
            throw new DateException("Невірний формат дати. Очікуваний: yyyy-MM-dd hh:mm:ss, але отримано " + dateTimeString);
        }
    }

}
