package ru.learn.learnSpring.api.response;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagListResponse {

    private int query;
    private List<TagResponse> tags;

    public int getQuery() {
        return query;
    }

    public void setQuery(int query) {
        this.query = query;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }
}
