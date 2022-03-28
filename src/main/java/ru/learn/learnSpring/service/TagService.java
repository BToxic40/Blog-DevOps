package ru.learn.learnSpring.service;

import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.TagListResponse;
import ru.learn.learnSpring.api.response.TagResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    public TagListResponse getTag() {
        TagListResponse tagList = new TagListResponse();
         tagList.setQuery(10);

        List<TagResponse> tagListResponses = new ArrayList<>();
        tagListResponses.add(new TagResponse());
        tagList.setTagResponses(tagListResponses);
         return tagList;
    }
}
