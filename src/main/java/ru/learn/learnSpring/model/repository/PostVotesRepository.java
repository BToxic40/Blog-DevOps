package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.PostVotes;
import java.util.List;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes, Integer> {

    @Override
    List<PostVotes> findAll();
}
