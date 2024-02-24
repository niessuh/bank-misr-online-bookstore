package com.bankmisr.onlinebookstore.repository;

import com.bankmisr.onlinebookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User,String> {
    @Query("{username: ?0}")
    User findByUsername(String username);
    User findByEmail(String email);
    Page<User> findUsersByRolesName(String roleName, Pageable pageable);

    int deleteUserByUsername(String userName);
}
