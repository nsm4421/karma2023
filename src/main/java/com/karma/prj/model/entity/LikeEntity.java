package com.karma.prj.model.entity;

import com.karma.prj.model.dto.LikeDto;
import com.karma.prj.model.dto.PostDto;
import com.karma.prj.model.util.AuditingFields;
import com.karma.prj.model.util.LikeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "\"like\"")
@SQLDelete(sql = "UPDATE \"like\" SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
public class LikeEntity extends AuditingFields {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne @JoinColumn(name = "post_id")
    private PostEntity post;
    @Enumerated(EnumType.STRING)
    private LikeType likeType;

    private LikeEntity(UserEntity user, PostEntity post, LikeType likeType) {
        this.user = user;
        this.post = post;
        this.likeType = likeType;
    }

    protected LikeEntity(){}

    public static LikeEntity of(UserEntity user, PostEntity post, LikeType likeType) {
        return new LikeEntity(user, post, likeType);
    }

    public static LikeDto dto(LikeEntity entity){
        return LikeDto.of(
                entity.getUser().getUsername(),
                entity.getPost().getId(),
                entity.getLikeType()
        );
    }
}
