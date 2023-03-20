package com.karma.community.model.entity;

import com.karma.community.model.util.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Table(name = "article")
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount author;

    @Column(name = "title", nullable = false)
    @Setter
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    @Setter
    private String content;

    /** 댓글
     * 부모 댓글 아이디, 생성일자 순으로 정렬
     */
    @ToString.Exclude
    @OrderBy("parentCommentId DESC, createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @ElementCollection
    @Setter
    private Set<String> images = new LinkedHashSet<String>();

    @ElementCollection
    @Setter
    private Set<String> hashtags = new LinkedHashSet<String>();

    private Article(UserAccount author, String title, String content, Set<String> images, Set<String> hashtags) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.images = images;
        this.hashtags = hashtags;
    }

    protected Article() {
    }

    public static Article of(UserAccount author, String title, String content, Set<String> images, Set<String> hashtags) {
        return new Article(author, title, content, images, hashtags);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
