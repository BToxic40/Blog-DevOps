package ru.learn.learnSpring.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.Post;

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
}
