package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Override
    <S extends User> List<S> saveAll(Iterable<S> iterable);

    @Query(value = "SELECT * FROM users where is_moderator = 1", nativeQuery = true)
    User findUserInfo(User id);

    @Query(value = "SELECT id FROM users where email = ?1 and password = ?2", nativeQuery = true)
    List<Integer> findAuth(String email, String password);

    Optional<User> findById(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `users` SET `password` = ?1 WHERE (`code` = ?2);", nativeQuery = true)
    void newPassword(@Param("password") String password, @Param("code") String code);

    @Query(value = "SELECT code FROM users where code = ?1", nativeQuery = true)
    List<String> findByCode(String code);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `users` SET `code` = ?1 WHERE (`email` = ?2);", nativeQuery = true)
    void changeCode(@Param("code") String code, @Param("email") String email);
}
