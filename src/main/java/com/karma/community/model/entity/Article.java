package com.karma.community.model.entity;

import com.karma.community.model.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
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
     * 부모 댓글 아이디 순으로 정렬
     */
    @ToString.Exclude
    @OrderBy("parentCommentId DESC")
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
}
