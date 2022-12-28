package com.example.demo.collections;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminCollection {
    @Id
    private String id;
    private Date time;
    private String method;
}
