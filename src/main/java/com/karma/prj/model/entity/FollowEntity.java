package com.karma.prj.model.entity;

import com.karma.prj.model.dto.FollowDto;
import com.karma.prj.model.util.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Setter
@Getter
@Entity
@Table(name = "follow")
@SQLDelete(sql = "UPDATE follow SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
public class FollowEntity extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH) @JoinColumn
    private UserEntity userFollowed;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH) @JoinColumn
    private UserEntity userFollowing;
    private FollowEntity(UserEntity userFollowed, UserEntity userFollowing) {
        this.userFollowed = userFollowed;
        this.userFollowing = userFollowing;
    }

    protected FollowEntity(){}

    public static FollowEntity of(UserEntity userFollowed, UserEntity userFollowing){
        return new FollowEntity(userFollowed, userFollowing);
    }

    public static FollowDto dto(FollowEntity entity){
        return FollowDto.of(
                UserEntity.dto(entity.getUserFollowed()),
                UserEntity.dto(entity.getUserFollowing())
        );
    }
}