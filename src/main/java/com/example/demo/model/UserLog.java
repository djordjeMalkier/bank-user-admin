package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actions")
@Data
@Builder
public class UserLog {
    @Id
    private String id;
    private String personalID;
    private String name;
    private String surname;
    private String address;
    private String method;
    private String error;
    private LocalDateTime createdTime;
}

