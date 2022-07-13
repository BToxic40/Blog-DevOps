package ru.learn.learnSpring.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "captcha_codes")
public class CaptchaCode {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "time", columnDefinition = "DATETIME")
    @NotNull
    private LocalDateTime time;

    @Column(name = "code", columnDefinition = "TINYTEXT")
    @NotNull
    private String code;

    @Column(name = "secret_code", columnDefinition = "TINYTEXT")
    @NotNull
    private String secretCode;
}
