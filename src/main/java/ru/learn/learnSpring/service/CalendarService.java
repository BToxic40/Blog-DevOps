package ru.learn.learnSpring.service;

import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.CalendarDaysResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class CalendarService {
    public CalendarDaysResponse calendarDaysResponse(String year) {

        int[] years = new int[]{2015, 2016};

        Map<String, Integer> amountPostsByDays = new HashMap<>();
        amountPostsByDays.put("2022-04-01", 3);
        amountPostsByDays.put("2022-04-02", 50);

        CalendarDaysResponse calendarDaysResponse = new CalendarDaysResponse();
        calendarDaysResponse.setYears(years);
        calendarDaysResponse.setPosts(amountPostsByDays);
        return calendarDaysResponse;
    }

}
