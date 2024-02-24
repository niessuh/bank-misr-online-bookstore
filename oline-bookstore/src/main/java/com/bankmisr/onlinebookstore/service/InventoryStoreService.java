package com.bankmisr.onlinebookstore.service;

import com.bankmisr.onlinebookstore.dto.InventoryStoreDto;
import com.bankmisr.onlinebookstore.exception.BadRequestException;

public interface InventoryStoreService {
    void increaseQuantityForBook(InventoryStoreDto dto);
    void decreaseQuantityForBook(InventoryStoreDto dto) throws BadRequestException;
    int getAvailableNumberOfThisBookInAllStock(String bookId);
    int getAvailableNumberOfThisBook(String bookId,String storeId);

}
