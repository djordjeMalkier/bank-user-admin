package com.example.demo.repository;

import com.example.demo.controller.dto.UserAdminDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserAdminDTO, String> {

}
