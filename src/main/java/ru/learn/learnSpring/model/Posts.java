package ru.learn.learnSpring.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Posts {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "is_active")
    @NotNull
    private String isActive;

    @Column(name = "moderation_status")
    @NotNull
    private String moderationStatus;

    @Column(name = "moderator_id")
    private int moderatorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private Users users;

    @Column(name = "time")
    @NotNull
    private Date time;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "text")
    @NotNull
    private String text;

    @Column(name = "view_count")
    @NotNull
    private int viewCount;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @OrderBy("time asc")
    private List<PostVotes> postVotes;

    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    @OrderBy("time asc")
    private List<PostComments> postComments;

    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    @OrderBy("time asc")
    private List<Tags> tags;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "tag2post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tags> tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(String moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public int getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(int moderatorId) {
        this.moderatorId = moderatorId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
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

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public List<Tags> getTag() {
        return tag;
    }

    public void setTag(List<Tags> tag) {
        this.tag = tag;
    }
}
