package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class TagListResponse {

//    private int query;
    private List<TagResponse> tags;

}
