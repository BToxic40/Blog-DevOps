package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.TagListResponse;
import ru.learn.learnSpring.api.response.TagResponse;
import ru.learn.learnSpring.model.TagWithCount;
import ru.learn.learnSpring.model.repository.PostRepository;
import ru.learn.learnSpring.model.repository.TagsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {
    private final PostRepository postRepository;
    private final TagsRepository tagRepository;

    public TagListResponse getTagList(String query) {
        log.info("tag = {}", query);
        return new TagListResponse(calculateWeightTag(query));
    }

    private List<TagResponse> calculateWeightTag(String query) {
        int countPublishedPosts = postRepository.countPublishedPosts();
        if (countPublishedPosts == 0) {
            return Collections.emptyList();
        }

        List<TagWithCount> tagWithCountList = tagRepository.findTagsWithPostsCount(query);

        if (tagWithCountList.isEmpty()) {
            return Collections.emptyList();
        }


        double k = 1.0 / ((double) tagWithCountList.get(0).getPostsCount() / countPublishedPosts);

        List<TagResponse> tagResponseList = new ArrayList<>();
        for (TagWithCount tagWithCount : tagWithCountList) {
            tagResponseList.add(
                    new TagResponse(tagWithCount.getName(),
                            k * ((double) tagWithCount.getPostsCount() / countPublishedPosts)));
        }
        return tagResponseList;
    }
}
