package com.bankmisr.onlinebookstore.repository;

import com.bankmisr.onlinebookstore.entity.InventoryStore;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryStoreRepository extends MongoRepository<InventoryStore,String> {
    Optional<InventoryStore> findByStoreIdAndBookId(String storeId,String bookId);
     List<InventoryStore> findAllByBookId(String bookId);
    List<InventoryStore> findAllByStoreIdAndBookId(String storeId,String bookId);
}
