package ru.learn.learnSpring.model.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Override
    <S extends User> List<S> findAll(Example<S> example);

    @Override
    List<User> findAllById(Iterable<Integer> integers);

    @Override
    <S extends User> List<S> saveAll(Iterable<S> iterable);

}
