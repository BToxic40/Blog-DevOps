package ru.learn.learnSpring.model.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.Post;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Override
    List<Post> findAll();

    @Override
    Optional<Post> findById(Integer integer);

    @Override
    <S extends Post> Optional<S> findOne(Example<S> example);

    @Override
    List<Post> findAllById(Iterable<Integer> integers);

    @Override
    <S extends Post> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Post> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Post> findAll(Sort sort);

    @Override
    Page<Post> findAll(Pageable pageable);
}
