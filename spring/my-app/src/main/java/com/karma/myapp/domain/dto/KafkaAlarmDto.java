package com.karma.myapp.domain.dto;

import com.karma.myapp.domain.constant.AlarmType;
import com.karma.myapp.domain.entity.AlarmEntity;

import java.time.LocalDateTime;

public record KafkaAlarmDto (
        Long id,
        Long uid,
        AlarmType alarmType,
        String message,
        String memo,
        LocalDateTime createdAt
){
    public static KafkaAlarmDto from(AlarmDto dto){
        return new KafkaAlarmDto(
                dto.id(),
                dto.user().id(),
                dto.alarmType(),
                dto.message(),
                dto.memo(),
                dto.createdAt()
        );
    }
    public static KafkaAlarmDto from(AlarmEntity entity){
        return from(AlarmDto.from(entity));
    }
}
