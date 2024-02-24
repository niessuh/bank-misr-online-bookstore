package com.bankmisr.onlinebookstore.repository;

import com.bankmisr.onlinebookstore.entity.Borrower;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BorrowerRepository extends MongoRepository<Borrower,String> {

}
