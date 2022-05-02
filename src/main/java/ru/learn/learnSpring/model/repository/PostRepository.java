package ru.learn.learnSpring.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active=1 AND moderation_status='ACCEPTED' AND time < now() " +
            "ORDER BY time DESC", nativeQuery = true)
    Page<Post> activeAcceptedPostsOrderByDateDesc(Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active=1 AND moderation_status='ACCEPTED' AND time < now() " +
            "ORDER BY time", nativeQuery = true)
    Page<Post> activeAcceptedPostsOrderByDate(Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "WHERE DATE_FORMAT(time, '%Y-%m-%d') = :dateWoTime " +
            "ORDER BY time", nativeQuery = true)
    Page<Post> findPostsByDate( Pageable pageable, @Param("dateWoTime") String date);

    @Query(value = "SELECT * FROM posts WHERE is_active = 0 AND user_id = :id", nativeQuery = true)
    Page<Post> findHiddenPostMy(Pageable pageable, int id);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 AND " +
            "user_id = :id AND " +
            "moderation_status = 'NEW'", nativeQuery = true)
    Page<Post> findIsActivePostMy(Pageable pageable, int id);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 AND " +
            "user_id = :id AND " +
            "moderation_status = 'DECLINED'", nativeQuery = true)
    Page<Post> findRejectedPostMy(Pageable pageable, int id);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 AND " +
            "user_id = :id AND " +
            "moderation_status = 'ACCEPTED'", nativeQuery = true)
    Page<Post> findAcceptedPostMy(Pageable pageable, int id);

    <S extends Post> S save(S entity);

    void deleteById(Optional<Post> post);

    <S extends Post> void saveAndFlush(int id);

    //List<Post> findAllById(Optional<User> id);

}
