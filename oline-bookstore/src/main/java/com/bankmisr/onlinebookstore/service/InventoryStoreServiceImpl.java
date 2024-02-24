package com.bankmisr.onlinebookstore.service;

import com.bankmisr.onlinebookstore.dto.InventoryStoreDto;
import com.bankmisr.onlinebookstore.entity.InventoryStore;
import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.repository.InventoryStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryStoreServiceImpl implements InventoryStoreService {
    private final InventoryStoreRepository inventoryStoreRepository;

    public InventoryStoreServiceImpl(InventoryStoreRepository inventoryStoreRepository) {
        this.inventoryStoreRepository = inventoryStoreRepository;
    }

    @Override
    public void increaseQuantityForBook(InventoryStoreDto dto) {
        InventoryStore inventoryStore=null;
        Optional<InventoryStore> bookInventoryOpt = getInventoryStore(dto.getStoreId(), dto.getBookId());
        if(bookInventoryOpt.isPresent()){
             inventoryStore=bookInventoryOpt.get();
            inventoryStore.setQuantity(inventoryStore.getQuantity() + dto.getQuantity());

        }else {
            inventoryStore=new InventoryStore();
            inventoryStore.setBookId(dto.getBookId());
            inventoryStore.setStoreId(dto.getStoreId());
            inventoryStore.setQuantity(dto.getQuantity());
        }

        inventoryStoreRepository.save(inventoryStore);

    }

    @Override
    public void decreaseQuantityForBook(InventoryStoreDto dto) throws BadRequestException {
        InventoryStore inventoryStore=null;
        Optional<InventoryStore> bookInventoryOpt = getInventoryStore(dto.getStoreId(), dto.getBookId());
        if(bookInventoryOpt.isPresent()){
            inventoryStore=bookInventoryOpt.get();
            inventoryStore.setQuantity(inventoryStore.getQuantity() -  dto.getQuantity());
        }else {
            throw new BadRequestException("No inventory for this book in this store");
        }
    }

    @Override
    public int getAvailableNumberOfThisBookInAllStock(String bookId) {
        List<InventoryStore> inventoryStoreList=inventoryStoreRepository.findAllByBookId(bookId);
        if(! inventoryStoreList.isEmpty()){
            return inventoryStoreList.stream().mapToInt(InventoryStore::getQuantity).sum();
        }
        return 0;
    }

    @Override
    public int getAvailableNumberOfThisBook(String bookId, String storeId) {
        List<InventoryStore> inventoryStoreList=inventoryStoreRepository.findAllByStoreIdAndBookId(storeId,bookId);
        if(! inventoryStoreList.isEmpty()){
            return inventoryStoreList.stream().mapToInt(InventoryStore::getQuantity).sum();
        }
        return 0;
    }

    private Optional<InventoryStore> getInventoryStore(String storeId, String bookId) {
        return inventoryStoreRepository.findByStoreIdAndBookId(storeId, bookId);
    }
}
