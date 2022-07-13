package ru.learn.learnSpring.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "global_settings")
public class GlobalSettings {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "code")
    @NotNull
    private String code;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "value")
    @NotNull
    private String value;
}
