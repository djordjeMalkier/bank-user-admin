package com.example.demo.aop;

import com.example.demo.collections.UserAdminCollection;
import com.example.demo.controller.dto.UserAdminDTO;
import com.example.demo.repository.UserRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserRequestAOP {
    private final UserRequestRepository userRequestRepository;
    @Pointcut("execution(* com.example.demo.service.UserAdminService.saveUser(..)) || " +
            "execution(* com.example.demo.service.UserAdminService.delete(..))")

    public void pointcut(){

    }
    @SuppressWarnings("unchecked cast")
    @Around("pointcut()")
    public Mono<UserAdminDTO> after(ProceedingJoinPoint joinPoint) {
        Mono<UserAdminDTO> userAdminDTOMono = null;

        try {
            userAdminDTOMono = (Mono<UserAdminDTO>) joinPoint.proceed();
            return userAdminDTOMono.publishOn(Schedulers.boundedElastic()).doOnSuccess(
                    user ->
                            userRequestRepository.insert(
                                    UserAdminCollection.builder()
                                            .method(joinPoint.getSignature().getName())
                                            .personalID(user.getPersonalId())
                                            .name(user.getName())
                                            .address(user.getAddress())
                                            .createdTime(LocalDateTime.now())
                                            .build())
            );

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return userAdminDTOMono;
    }
}