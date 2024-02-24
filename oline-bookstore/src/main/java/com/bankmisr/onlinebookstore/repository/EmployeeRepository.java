package com.bankmisr.onlinebookstore.repository;

import com.bankmisr.onlinebookstore.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")
public interface EmployeeRepository extends MongoRepository<Employee,String> {

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    <S extends Employee> S save(S entity);

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    List<Employee> findAll();

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    Optional<Employee> findById(String id);
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    void deleteById(String id);


}
