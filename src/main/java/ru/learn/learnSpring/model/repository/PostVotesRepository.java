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

    @Query(value="SELECT COUNT(*) FROM post_votes " +
            "JOIN posts ON posts.id = post_votes.post_id " +
            "WHERE post_votes.value = ?1 " +
            "AND posts.is_active = 1 " +
            "AND posts.moderation_status = 'ACCEPTED' ", nativeQuery = true)
    int countVotes(int voteValue);

    @Query(value="SELECT COUNT(*) FROM post_votes pv " +
            "JOIN posts p ON p.id = pv.post_id " +
            "WHERE pv.value = ?1 " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "AND p.user_id = ?2", nativeQuery = true)
    int countVotesForUserId(int voteValue, int userId);
}
