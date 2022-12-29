package com.example.demo.collections;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actions")
@Data
@Builder
public class UserAdminCollection {
    @Id
    private String id;
    private String personalID;
    private String name;
    private String address;
    private String method;
    private String error;
    private LocalDateTime createdTime;
}

