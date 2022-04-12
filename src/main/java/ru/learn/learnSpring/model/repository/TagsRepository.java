package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.Tags;
import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {
    List<Tags> findAll();
}
