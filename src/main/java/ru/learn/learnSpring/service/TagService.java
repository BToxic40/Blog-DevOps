package ru.learn.learnSpring.service;

import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.TagListResponse;
import ru.learn.learnSpring.api.response.TagResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    public TagListResponse getTagList(String searchQuery) {
        TagResponse tagResponse = new TagResponse();
        tagResponse.setName("tags");
        tagResponse.setWeight(0.7);

        TagResponse tagResponse2 = new TagResponse();
        tagResponse2.setName("Java");
        tagResponse2.setWeight(0.5);

        List<TagResponse> tagListResponses = new ArrayList<>();
        tagListResponses.add(tagResponse);
        tagListResponses.add(tagResponse2);


        TagListResponse tagList = new TagListResponse();
        tagList.setTags(tagListResponses);
         return tagList;
    }
}
