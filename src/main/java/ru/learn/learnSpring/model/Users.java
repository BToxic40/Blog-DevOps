package ru.learn.learnSpring.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class Users {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "is_moderator")
    @NotNull
    private String isModerator;

    @Column(name = "reg_time")
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

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<PostVotes> postVotes;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<PostComments> postComments;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Posts> posts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(String isModerator) {
        this.isModerator = isModerator;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<PostVotes> getPostVotes() {
        return postVotes;
    }

    public void setPostVotes(List<PostVotes> postVotes) {
        this.postVotes = postVotes;
    }

    public List<PostComments> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComments> postComments) {
        this.postComments = postComments;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }
}
