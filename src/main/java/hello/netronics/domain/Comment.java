package hello.netronics.domain;

import hello.netronics.domain.Post;
import hello.netronics.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    // 부모 댓글을 참조하는 필드 (대댓글일 경우)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 대댓글 목록 (해당 댓글의 대댓글들)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    @Column(nullable = false)
    private boolean isReply = false;  // 대댓글 여부를 표시하는 필드

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 댓글인지 대댓글인지 구분하는 메소드
    public boolean isReply() {
        return this.isReply;
    }

    public void setAsReply(Comment parent) {
        this.isReply = true;
        this.parent = parent;
    }

    // 기본 생성자
    public Comment() {}

    // 댓글 생성자
    public Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // 대댓글 생성자
    public Comment(Post post, User user, String content, Comment parent) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.parent = parent;
        this.isReply = true;
        this.createdAt = LocalDateTime.now();
    }

    // getters and setters
}