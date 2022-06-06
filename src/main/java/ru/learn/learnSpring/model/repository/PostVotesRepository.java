package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.Post;
import ru.learn.learnSpring.model.PostVote;
import ru.learn.learnSpring.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVote, Integer> {

    @Override
    List<PostVote> findAll();

    List<PostVote> findAllByPostId(int PostId);
    Optional<PostVote> findByPostAndUser(Post post, User user);

    @Query(value="SELECT COUNT(value) FROM post_votes  where post_id =?1 AND value =?2", nativeQuery = true)
    Integer countVotesByPost(int postId, int typeLike);
}
