package com.example.demo.service;

import com.example.demo.controller.dto.AuthenticationRequest;
import com.example.demo.controller.dto.UserAdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private WebClient webClient;

    @Autowired
    public UserAdminService(WebClient.Builder builder) {
        webClient = builder.baseUrl("http://localhost:8080").build();
    }
    public Mono<UserAdminDTO> saveUser(UserAdminDTO user, String token) {
        return webClient
                .post()
                .uri("/users/add")
                .body(Mono.just(user), UserAdminDTO.class)
                .headers(h -> h.setBearerAuth(token.substring(7)))
                .retrieve()
                .bodyToMono(UserAdminDTO.class);
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
        return webClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/delete")
                        .queryParam("id", id).build())
                .headers(h -> h.setBearerAuth(token.substring(7)))
                .retrieve()
                .bodyToMono(UserAdminDTO.class);
    }

}
