package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.PostComments;

import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComments, Integer> {
    @Query(value = "select p from post_comments p WHERE id = :id", nativeQuery = true)
    Optional<PostComments> findOneById(@Param("id") Integer id);
}
