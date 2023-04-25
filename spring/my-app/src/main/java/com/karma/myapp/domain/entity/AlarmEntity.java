package com.karma.myapp.domain.entity;

import com.karma.myapp.domain.constant.AlarmType;
import com.karma.myapp.domain.constant.BaseEntity;
import com.karma.myapp.domain.constant.EmotionConst;
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
@Table(name = "ALARM")
@SQLDelete(sql = "UPDATE ALARM SET removed_at = NOW() WHERE id=?")   // soft delete
@Where(clause = "removed_at is NULL")
@EntityListeners(AuditingEntityListener.class)  // jpa auditing
public class AlarmEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity user;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "alarm_type", nullable = false)
    private AlarmType alarmType;
    private String message;
    @Column(columnDefinition = "json")
    private String memo;

    private AlarmEntity(Long id, UserAccountEntity user, AlarmType alarmType, String message, String memo) {
        this.id = id;
        this.user = user;
        this.alarmType = alarmType;
        this.message = message;
        this.memo = memo;
    }

    protected AlarmEntity() {
    }

    public static AlarmEntity of(UserAccountEntity user, AlarmType alarmType, String message) {
        return new AlarmEntity(null, user, alarmType, message, null);
    }

    public static AlarmEntity of(UserAccountEntity user, AlarmType alarmType, String message, String memo) {
        return new AlarmEntity(null, user, alarmType, message, memo);
    }
}
