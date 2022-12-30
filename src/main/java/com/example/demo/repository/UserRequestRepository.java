package com.example.demo.repository;

import com.example.demo.model.UserLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRequestRepository extends MongoRepository<UserLog, String> {

}
