package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.PostComments;

@Repository
public interface CommentsRepository extends JpaRepository<PostComments, Integer> {

}
