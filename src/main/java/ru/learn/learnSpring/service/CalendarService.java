package ru.learn.learnSpring.service;

import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.PostCalendarResponse;
import ru.learn.learnSpring.model.repository.PostRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CalendarService {
    private final PostRepository postRepository;

    public CalendarService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    public PostCalendarResponse getPosts(Integer year) {

        if (year == 0) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        List<String> date = postRepository.findYear(year);
        List<Integer> dateCount = postRepository.findYearCount(year);

        PostCalendarResponse postCalendar = new PostCalendarResponse();
        Map<String, Integer> calendar = new TreeMap<>();
        for (int i = 0; i < date.size(); i++) {
            calendar.put(date.get(i), dateCount.get(i));

        }
        postCalendar.setPosts(calendar);
        List<Integer> yearsList = postRepository.findYearsForCalendar();
        postCalendar.setYears(yearsList);

        return postCalendar;
    }

}
