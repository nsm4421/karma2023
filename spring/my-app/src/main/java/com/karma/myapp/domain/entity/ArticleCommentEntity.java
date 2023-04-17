package com.karma.myapp.domain.entity;

import com.karma.myapp.domain.constant.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "ARTICLE_COMMENT")
@SQLDelete(sql = "UPDATE ARTICLE_COMMENT SET removed_at = NOW() WHERE id=?")   // soft delete
@Where(clause = "removed_at is NULL")
@EntityListeners(AuditingEntityListener.class)  // jpa auditing
public class ArticleCommentEntity extends BaseEntity {
    /**
     * id
     * article : 게시글
     * user : 댓쓴이
     * content : 댓글내용
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @Setter
    private ArticleEntity article;          // ArticleEntity.java 파일에서 mapped.by에 사용한 명칭과 동일하게 article으로 작명
    @ManyToOne(optional = false) @JoinColumn(name = "user_id") @Setter
    private UserAccountEntity user;
    @Column(nullable = false, columnDefinition = "TEXT") @Setter
    private String content;

    private ArticleCommentEntity(Long id, ArticleEntity article, UserAccountEntity user, String content) {
        this.id = id;
        this.article = article;
        this.user = user;
        this.content = content;
    }

    protected ArticleCommentEntity(){}

    public static ArticleCommentEntity of(ArticleEntity article, UserAccountEntity user, String content){
        return new ArticleCommentEntity(null, article, user, content);
    }
}
