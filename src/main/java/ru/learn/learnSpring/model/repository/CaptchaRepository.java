package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.CaptchaCode;

import java.util.Collection;
import java.util.List;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCode, Integer> {

    @Query(value = "SELECT * FROM captcha_codes where secret_code = :code",
            nativeQuery = true)
    List<CaptchaCode> findAllEqualsSecretCode(@Param("code") String secretCode);

    Collection<CaptchaCode> findBySecretCode(String secretCode);

    @Query(value = "SELECT id FROM captcha_codes where code = ?1 and secret_code=?2", nativeQuery = true)
    public List<String> findByCaptcha(String captcha, String captchaSecret);

    Collection<CaptchaCode> findById(int id);

    @Modifying
    @Query(value = "DELETE FROM captcha_codes where timediff(now(), time) > '01:00:00';",
            nativeQuery = true)
    void delete();
}
