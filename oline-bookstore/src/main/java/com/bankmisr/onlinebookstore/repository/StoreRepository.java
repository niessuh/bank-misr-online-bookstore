package com.bankmisr.onlinebookstore.repository;

import com.bankmisr.onlinebookstore.entity.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "store", path = "store")
public interface StoreRepository extends MongoRepository<Store,String> {

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    <S extends Store> S save(S entity);

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    List<Store> findAll();

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    Optional<Store> findById(String id);

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    void deleteById(String id);
}
