package com.bankmisr.onlinebookstore.controller;

import com.bankmisr.onlinebookstore.dto.InventoryStoreDto;
import com.bankmisr.onlinebookstore.service.InventoryStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = { "InventoryStore Operations APIs" })
@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping("/book-store/v1/api")
public class InventoryStoreController {
    private final InventoryStoreService inventoryStoreService;

    public InventoryStoreController(InventoryStoreService inventoryStoreService) {
        this.inventoryStoreService = inventoryStoreService;
    }

    @ApiOperation(value = "This API is used to increase book Quantity.")
    @PostMapping(value = "/increase/quantity")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Object> increaseQuantityBook(@RequestBody @Valid InventoryStoreDto dto){
        inventoryStoreService.increaseQuantityForBook(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to decrease book Quantity.")
    @PostMapping(value = "/decrease/quantity")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Object> decreaseQuantityBook(@RequestBody @Valid InventoryStoreDto dto){
        inventoryStoreService.increaseQuantityForBook(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get available book Quantity.")
    @GetMapping(value = "/quantity/{bookId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Integer> getAvailableBookQuantity(@PathVariable String bookId){
        return new ResponseEntity<>(inventoryStoreService.getAvailableNumberOfThisBookInAllStock(bookId),HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get all available book Quantity.")
    @GetMapping(value = "/quantity/{storeId}/{bookId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Integer> getAvailableBookQuantityInStore(@PathVariable String storeId,@PathVariable String bookId){
        return new ResponseEntity<>(inventoryStoreService.getAvailableNumberOfThisBook(storeId,bookId),HttpStatus.OK);
    }

}
