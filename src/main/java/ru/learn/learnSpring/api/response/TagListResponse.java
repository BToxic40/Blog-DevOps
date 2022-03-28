package ru.learn.learnSpring.api.response;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagListResponse {

    private int query;
    private List<TagResponse> tagResponses;

    public int getQuery() {
        return query;
    }

    public void setQuery(int query) {
        this.query = query;
    }

    public List<TagResponse> getTagResponses() {
        return tagResponses;
    }

    public void setTagResponses(List<TagResponse> tagResponses) {
        this.tagResponses = tagResponses;
    }
}
