package com.example.demo.service;

import com.example.demo.collections.UserAdminCollection;
import com.example.demo.controller.dto.AuthenticationRequest;
import com.example.demo.controller.dto.UserAdminDTO;
import com.example.demo.repository.UserCollectionRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private WebClient webClient;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Autowired
    public UserAdminService(WebClient.Builder builder) {
        webClient = builder.baseUrl("http://localhost:8080").build();
    }
    public Mono<UserAdminDTO> saveUser(UserAdminDTO user, String token) {
        UserAdminCollection collection = new UserAdminCollection();
        collection.setTime(new Date());
        collection.setMethod("saveUser");

        return webClient
                .post()
                .uri("/users/add")
                .body(Mono.just(user), UserAdminDTO.class)
                .headers(h -> h.setBearerAuth(token.substring(7)))
                .retrieve()
                .bodyToMono(UserAdminDTO.class)
                .doOnSuccess(userRepository::save)
                .doOnSuccess(savedUser -> userCollectionRepository.save(collection));
    }

    public String authenticate(AuthenticationRequest authenticationRequest) {
        return webClient
                .post()
                .uri("/auth/authenticate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(authenticationRequest),AuthenticationRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public Mono<UserAdminDTO> delete(String id, String token) {
        UserAdminCollection collection = new UserAdminCollection();
        collection.setTime(new Date());
        collection.setMethod("delete");

        return webClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/delete")
                        .queryParam("id", id).build())
                .headers(h -> h.setBearerAuth(token.substring(7)))
                .retrieve()
                .bodyToMono(UserAdminDTO.class)
                .doOnSuccess(userRepository::save)
                .doOnSuccess(savedUser -> userCollectionRepository.save(collection));
    }
    public void saveAction(UserAdminCollection collection) {
        userCollectionRepository.save(collection);
    }
    public UserAdminDTO findById(String personalID) {
        return userRepository.findById(personalID).orElse(null);
    }

}
