package ru.learn.learnSpring.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.learn.learnSpring.api.request.PostApi;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostListApi extends ResponseApi {

    private List<PostApi> data = new ArrayList<>();
    private int total;
    private int offset;
    private int perPage;

    @JsonProperty("data")
    public List<PostApi> getData() {
        return data;
    }
}
