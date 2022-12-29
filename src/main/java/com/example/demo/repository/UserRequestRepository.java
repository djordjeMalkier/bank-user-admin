package com.example.demo.repository;

import com.example.demo.aop.UserRequestAOP;
import com.example.demo.collections.UserAdminCollection;
import com.example.demo.controller.UserAdminController;
import com.example.demo.controller.dto.UserAdminDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRequestRepository extends MongoRepository<UserAdminCollection, String> {

}
