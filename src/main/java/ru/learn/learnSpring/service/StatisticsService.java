package ru.learn.learnSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.learn.learnSpring.api.response.StatisticResponse;
import ru.learn.learnSpring.model.User;
import ru.learn.learnSpring.model.repository.PostRepository;
import ru.learn.learnSpring.model.repository.PostVotesRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {
    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;
    private final AuthService authService;

    public StatisticResponse collectCurrentUserStatistic() {
        User currentUser = authService.getCurrentUser().orElseThrow();
        return StatisticResponse.builder()
                .postsCount(postRepository.countPublishedByAuthorId(currentUser.getId()))
                .dislikesCount(postVotesRepository.countVotesForUserId(-1, currentUser.getId()))
                .likesCount(postVotesRepository.countVotesForUserId(1, currentUser.getId()))
                .viewsCount(postRepository.countAllViewsForAuthorId(currentUser.getId()))
                .firstPublication(
                        getSeconds(postRepository.firstPublishedPostByAuthorId(currentUser.getId()).orElseThrow().getTime())
                ).build();
    }

    public StatisticResponse collectAllStatistic() {
        return StatisticResponse.builder()
                .postsCount(postRepository.countPublished())
                .dislikesCount(postVotesRepository.countVotes(-1))
                .likesCount(postVotesRepository.countVotes(1))
                .viewsCount(postRepository.countAllViews())
                .firstPublication(
                        getSeconds(postRepository.firstPublishedPost().orElseThrow().getTime())
                ).build();
    }

    private long getSeconds(LocalDateTime time) {
        log.info("date tot convert = {}", time);
        ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli() / 1000;
    }
}
