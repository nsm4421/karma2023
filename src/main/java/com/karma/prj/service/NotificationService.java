package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.dto.NotificationDto;
import com.karma.prj.model.entity.NotificationEntity;
import com.karma.prj.repository.EmitterRepository;
import com.karma.prj.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;
    private final static Long TIMEOUT = 360000L;
    private final static String EVENT_NAME = "notification";    // EventSource(front-end)의 이벤트명

    /**
     * Sse Emitter 보내기
     * @param userId : sse emitter 보낼 유저 아이디
     * @param notificationId : sse emitter 보낼 알림 아이디
     */
    public void sendNotification(Long userId, Long notificationId){
        emitterRepository.getEmitter(userId).ifPresentOrElse(
                sseEmitter -> {
                    try{
                        sseEmitter.send(SseEmitter.event().id(userId.toString()).name(EVENT_NAME));
                    } catch (IOException err){
                        emitterRepository.removeEmitter(notificationId);
                        throw CustomException.of(CustomErrorCode.ERROR_ON_CREATE_SseEmitter);
                    }},
                ()->{
                    log.warn("No emitter was founded... uid :{}", userId);
                });
    }
    
    public SseEmitter connectNotification(Long userId){
        SseEmitter sseEmitter = new SseEmitter(TIMEOUT);
        emitterRepository.setEmitter(userId, sseEmitter);
        sseEmitter.onCompletion(()->emitterRepository.removeEmitter(userId));
        sseEmitter.onTimeout(()->emitterRepository.removeEmitter(userId));
        try{
            sseEmitter.send(SseEmitter.event().id(userId.toString()).name(EVENT_NAME).data("connected..."));
        } catch (IOException err){
            throw CustomException.of(CustomErrorCode.ERROR_ON_CREATE_SseEmitter);
        }
        return sseEmitter;
    }

    /**
     * 알림 가져오기
     * @param userId 알림을 가져올 유저 id
     * @param pageable 페이지
     * @return Notification Dto Page
     */
    @Transactional(readOnly = true)
    public Page<NotificationDto> getNotification(Long userId, Pageable pageable){
        return notificationRepository.findAllByUserId(userId, pageable).map(NotificationEntity::dto);
    }

    /**
     * 알림 삭제하기
     */
    @Transactional
    public void deleteNotificationById(Long userId, Long notificationId){
        NotificationEntity notification = findByNotificationIdOrElseThrow(notificationId);
        if (!notification.getUser().getId().equals(userId)){
            throw CustomException.of(CustomErrorCode.NOT_GRANTED_ACCESS);
        }
        notificationRepository.delete(notification);
    }

    /**
     * 알림 삭제하기
     */
    @Transactional
    public void deleteAllNotification(Long userId){
        notificationRepository.deleteAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    private NotificationEntity findByNotificationIdOrElseThrow(Long notification){
        return notificationRepository.findById(notification)
                .orElseThrow(()->{throw CustomException.of(CustomErrorCode.NOTIFICATION_NOT_FOUND);});
    }
}
