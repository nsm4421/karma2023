package com.karma.myapp.service;

import com.karma.myapp.domain.constant.AlarmType;
import com.karma.myapp.domain.entity.AlarmEntity;
import com.karma.myapp.domain.entity.UserAccountEntity;
import com.karma.myapp.exception.CustomErrorCode;
import com.karma.myapp.exception.CustomException;
import com.karma.myapp.repository.AlarmRepository;
import com.karma.myapp.repository.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final SseEmitterRepository sseEmitterRepository;

    @Value("${sse.timeout}")
    private Long TIMEOUT;
    @Value("${sse.event-name}")
    private String EVENT_NAME;

    public void sendAlarm(AlarmEntity entity) {
        sseEmitterRepository.getEmitter(entity.getUser().getUsername()).ifPresentOrElse(it -> {
            try {
                it.send(SseEmitter
                        .event()
                        .id(entity.getId().toString())
                        .name(EVENT_NAME)
                        .data(entity.getMessage())
                );
            } catch (IOException e) {
                final String errorMessage = String.format("Saving alarm failed - AlarmId:%s", entity.getId());
                log.error(errorMessage);
                throw CustomException.of(CustomErrorCode.INTERNAL_SERVER_ERROR, errorMessage);
            }
        }, () ->  log.info("No Emitter found"));
    }

    public SseEmitter connect(String username){
        SseEmitter sseEmitter = new SseEmitter(TIMEOUT);
        sseEmitterRepository.saveEmitter(username, sseEmitter);
        sseEmitter.onCompletion(()->sseEmitterRepository.removeEmitter(username));
        sseEmitter.onTimeout(()->sseEmitterRepository.removeEmitter(username));
        try{
            sseEmitter.send(SseEmitter.event().id(username).name(EVENT_NAME));
        } catch (IOException ioException){
            throw CustomException.of(CustomErrorCode.INTERNAL_SERVER_ERROR, "Sse connection failed");
        }
        return sseEmitter;
    }

    public AlarmEntity saveAlarm(UserAccountEntity user, AlarmType alarmType, String message, String memo){
        return alarmRepository.save(AlarmEntity.of(user, alarmType, message, memo));
    }
}
