package ru.learn.learnSpring.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "is_moderator", columnDefinition = "TINYINT")
    @NotNull
    private String isModerator;

    @Column(name = "reg_time", columnDefinition = "DATETIME")
    @NotNull
    private Date regTime;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "code")
    private String code;

    @Column(name = "photo",columnDefinition = "TEXT")
    private String photo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<PostVotes> postVotes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<PostComments> postComments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

//    private Role getRole() {
//        return isModerator == 1 ? Role.MODERATOR : Role.USER;
//    }

}
