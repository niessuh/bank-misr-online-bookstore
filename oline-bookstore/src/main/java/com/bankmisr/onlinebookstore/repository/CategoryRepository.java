package com.bankmisr.onlinebookstore.repository;

import com.bankmisr.onlinebookstore.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "category", path = "category")
public interface CategoryRepository extends MongoRepository<Category,String> {

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    <S extends Category> S save(S entity);

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    List<Category> findAll();

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    Optional<Category> findById(String id);

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    void deleteById(String id);

}
