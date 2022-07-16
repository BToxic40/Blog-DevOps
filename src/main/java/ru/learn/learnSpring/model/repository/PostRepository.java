package ru.learn.learnSpring.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.learn.learnSpring.model.ModerationStatus;
import ru.learn.learnSpring.model.Post;
import ru.learn.learnSpring.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active=1 AND moderation_status='ACCEPTED' AND time < now() " +
            "ORDER BY time DESC", nativeQuery = true)
    Page<Post> findByRecent(Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active=1 AND moderation_status='ACCEPTED' AND time < now() " +
            "ORDER BY time", nativeQuery = true)
    Page<Post> findByEarly(Pageable pageable);

    @Query(value = "FROM Post p " +
            "WHERE p.isActive=1 AND p.moderationStatus='ACCEPTED' AND p.time < now() " +
            "order by p.postComments.size desc ")
    Page<Post> findByPopular(Pageable pageable);

    @Query(value = "FROM Post p " +
            "WHERE p.isActive=1 AND p.moderationStatus='ACCEPTED' AND p.time < now() " +
            "order by p.postVotes.size desc ")
    Page<Post> findByBest(Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "WHERE DATE_FORMAT(time, '%Y-%m-%d') = :dateWoTime " +
            "AND is_active=1 AND moderation_status='ACCEPTED' " +
            "ORDER BY time", nativeQuery = true)
    Page<Post> findPostsByDate(Pageable pageable, @Param("dateWoTime") String date);

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

    @Query(value = "SELECT * FROM posts " +
            "where is_active = 1 AND moderation_status = ?1 " +
            "limit ?2 offset ?3", nativeQuery = true)
    List<Post> findByStatus(String status, int limit, int offset);

    @Query(value = "SELECT * FROM posts " +
            "where moderation_status = ?1 " +
            "limit ?2 offset ?3", nativeQuery = true)
    List<Post> findForModerationByStatus(String status, int limit, int offset);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `posts` SET `moderation_status` = ?1 WHERE (`id` = ?2)", nativeQuery = true)
    void changeStatus(@Param("status") String status, @Param("postId") Integer postId);

    @Query("select count(*) from Post p WHERE p.moderationStatus = ?1")
    int getPostsForModerationCount(ModerationStatus status);

    @Query(value = "SELECT COUNT(*) FROM posts where moderation_status = 'NEW'", nativeQuery = true)
    Integer findByModerationStatus();

    @Query(value = "SELECT COUNT(id) FROM posts " +
            "WHERE is_active=1 AND moderation_status='ACCEPTED' AND time < now() ", nativeQuery = true)
    int countPublishedPosts();

    void deleteById(Optional<Post> post);

    <S extends Post> void saveAndFlush(int id);

    List<Post> findAllById(Optional<User> id);

    @Query(value = "from Post p WHERE p.time <= now() AND id = :id")
    Post findPostById(@Param("id") Integer id);

    @Query(value = "SELECT date(date(time))  FROM posts " +
            "where year(time)=?1 AND is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "group by date(time) " +
            "order by time", nativeQuery = true)
    List<String> findYear(int year);

    @Query(value = "SELECT count(date(time)) FROM posts " +
            "where year(time)=?1 AND is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "group by date(time) " +
            "order by time", nativeQuery = true)
    List<Integer> findYearCount(int year);

    @Query(value = "SELECT year(time) FROM posts group by year(time)", nativeQuery = true)
    List<Integer> findYearsForCalendar();

    @Query(value = "SELECT count(*) FROM posts " +
            "where is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "AND time < now()", nativeQuery = true)
    int countPublished();

    @Query(value = "SELECT count(*) FROM posts " +
            "where is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "AND time < now() AND user_id = ?1", nativeQuery = true)
    int countPublishedByAuthorId(int authorId);

    @Query(value = "SELECT SUM(p.view_count) FROM posts p " +
            "WHERE p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "AND p.user_id = ?1", nativeQuery = true)
    int countAllViewsForAuthorId(int authorId);

    @Query(value = "SELECT SUM(p.view_count) FROM posts p " +
            "WHERE p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED'", nativeQuery = true)
    int countAllViews();

    @Query(value = "SELECT * FROM posts p " +
            "    WHERE p.is_active = 1 " +
            "    AND p.moderation_status = 'ACCEPTED' " +
            "    ORDER BY p.time " +
            "    LIMIT 1", nativeQuery = true)
    Optional<Post> firstPublishedPost();

    @Query(value = "SELECT * FROM posts p " +
            "    WHERE p.is_active = 1 " +
            "    AND p.moderation_status = 'ACCEPTED' " +
            "    ORDER BY p.time " +
            " AND p.user_id = ?1 " +
            "    LIMIT 1", nativeQuery = true)
    Optional<Post> firstPublishedPostByAuthorId(int authorId);

    @Query(value = "SELECT * FROM posts p " +
            "WHERE p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "AND (p.title LIKE %?1% OR p.text LIKE %?1%) " +
            "ORDER BY p.view_count DESC", nativeQuery = true)
    Page<Post> search(Pageable pageable, String query);

}
