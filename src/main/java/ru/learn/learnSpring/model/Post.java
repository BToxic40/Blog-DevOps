package ru.learn.learnSpring.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_active")
    private int isActive;

    @Column(name = "moderation_status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ModerationStatus moderationStatus;

    @Column(name = "moderator_id")
    private int moderatorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "time", columnDefinition = "DATETIME")
    @NotNull
    private LocalDateTime time;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "text", columnDefinition = "MEDIUMTEXT")
    @NotNull
    private String text;

    @Column(name = "view_count")
    @NotNull
    private int viewCount;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostVote> postVotes;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostComments> postComments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag2post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tag;
}
