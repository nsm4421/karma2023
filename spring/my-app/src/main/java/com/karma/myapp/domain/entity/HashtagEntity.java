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
@Table(name = "HASHTAG")
@SQLDelete(sql = "UPDATE HASHTAG SET removed_at = NOW() WHERE id=?")   // soft delete
@Where(clause = "removed_at is NULL")
@EntityListeners(AuditingEntityListener.class)  // jpa auditing
public class HashtagEntity extends BaseEntity {
    /**
     * 해시태그
     * id : primary key
     * article : 해시태그가 있는 게시글
     * content : 해시태그 내용
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private ArticleEntity article;
    @Setter private String content;

    private HashtagEntity(Long id, ArticleEntity article, String content) {
        this.id = id;
        this.article = article;
        this.content = content;
    }

    protected HashtagEntity(){}

    public static HashtagEntity of(ArticleEntity article, String content){
        return new HashtagEntity(null, article, content);
    }
}
