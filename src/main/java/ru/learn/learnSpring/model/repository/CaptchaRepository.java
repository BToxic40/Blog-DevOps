package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.CaptchaCodes;
import java.util.List;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCodes, String> {
    @Override
    default List<CaptchaCodes> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    default <S extends CaptchaCodes> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    default void deleteAllInBatch() {

    }
}
