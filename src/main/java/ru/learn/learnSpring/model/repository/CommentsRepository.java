package ru.learn.learnSpring.model.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.PostComments;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<PostComments, Integer> {
    @Override
    List<PostComments> findAll();

    @Override
    <S extends PostComments> List<S> findAll(Example<S> example, Sort sort);

    @Override
    List<PostComments> findAll(Sort sort);

    @Override
    <S extends PostComments> List<S> saveAll(Iterable<S> entities);

//    @Override
//    <S extends PostComments> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    List<PostComments> findAllById(Iterable<Integer> integers);

    @Override
    <S extends PostComments> List<S> findAll(Example<S> example);

    @Override
    Optional<PostComments> findById(Integer integer);
}
