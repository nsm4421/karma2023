package com.karma.prj.model.entity;

import com.karma.prj.model.dto.PostDto;
import com.karma.prj.model.dto.UserDto;
import com.karma.prj.model.util.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "\"post\"")
@SQLDelete(sql = "UPDATE \"post\" SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
public class PostEntity extends AuditingFields {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;
    @ManyToOne @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(fetch = FetchType.LAZY) @JoinColumn(name = "post_id")
    private List<CommentEntity> comments;

    public static PostEntity of(String title, String content, UserEntity user) {
        PostEntity entity = new PostEntity();
        entity.setTitle(title);
        entity.setContent(content);
        entity.setUser(user);
        return entity;
    }

    public static PostDto dto(PostEntity entity){
        return PostDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                UserEntity.dto(entity.getUser()),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
