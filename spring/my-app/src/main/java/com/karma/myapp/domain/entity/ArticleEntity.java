package com.karma.myapp.domain.entity;

import com.karma.myapp.domain.constant.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@ToString(callSuper = true)
@Table(
        name = "ARTICLE",
        indexes = {
                @Index(columnList = "title")
        })
@SQLDelete(sql = "UPDATE ARTICLE SET removed_at = NOW() WHERE id=?")   // soft delete
@Where(clause = "removed_at is NULL")
@EntityListeners(AuditingEntityListener.class)  // jpa auditing
public class ArticleEntity extends BaseEntity {
    /**
     * id : primary key
     * user : 글쓴이
     * title : 게시글 제목
     * content : 게시글 본문
     * hashtags : 해쉬태그
     * comments : 댓글
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccountEntity user;
    @Column(nullable = false)
    @Setter
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    @Setter
    private String content;
    @ToString.Exclude
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "hashtag")
    @Setter
    private Set<String> hashtags = new HashSet<String>();
    @ToString.Exclude
    @OrderBy("created_at DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleCommentEntity> comments = new LinkedHashSet<>();

    private ArticleEntity(Long id, UserAccountEntity user, String title, String content, Set<String> hashtags) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.hashtags = hashtags;
    }

    protected ArticleEntity() {
    }

    public static ArticleEntity of(
            UserAccountEntity user,
            String title,
            String content
    ) {
        return new ArticleEntity(null, user, title, content, Set.of());
    }

    public static ArticleEntity of(
            UserAccountEntity user,
            String title,
            String content,
            Set<String> hashtags
    ) {
        return new ArticleEntity(null, user, title, content, hashtags);
    }
}