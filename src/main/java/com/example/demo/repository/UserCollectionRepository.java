package com.example.demo.repository;

import com.example.demo.collections.UserAdminCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCollectionRepository extends MongoRepository<UserAdminCollection, String> {
}
