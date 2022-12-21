package com.example.demo.controller;

import com.example.demo.controller.dto.AuthenticationRequest;
import com.example.demo.controller.dto.UserAdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.example.demo.service.UserAdminService;

@RequestMapping(value = "/users", method = RequestMethod.GET)
@RequiredArgsConstructor
@RestController
public class UserAdminController {
    private final UserAdminService userAdminService;

    @PostMapping("/add")
    public Mono<UserAdminDTO> sendPostRequest(@RequestBody UserAdminDTO user, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return userAdminService.saveUser(user, token);
    }

    @DeleteMapping("/delete")
    public Mono<UserAdminDTO> delete(@RequestParam String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return userAdminService.delete(id, token);
    }

    @PostMapping("/auth")
    public String authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        System.out.println("ispis");
        return userAdminService.authenticate(authenticationRequest);
    }
}
