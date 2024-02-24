package com.bankmisr.onlinebookstore.dto;

import com.bankmisr.onlinebookstore.constant.AppConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class BookDTO {

    @NotBlank(message = AppConstants.INVALID_FIELD + "bookName")
    private String bookName;

    @NotBlank(message = AppConstants.INVALID_FIELD + "title")
    private String title;

    @NotBlank(message = AppConstants.INVALID_FIELD + "author")
    private String author;

    @NotBlank(message = AppConstants.INVALID_FIELD + "publisher")
    private String publisher;

    @NotBlank(message = AppConstants.INVALID_FIELD + "publicationYear")
    private String publicationYear;

    @NotBlank(message = AppConstants.INVALID_FIELD + "categoryId")
    private String categoryId;

    @NotBlank(message = AppConstants.INVALID_FIELD + "storeId")
    private String storeId;

    @Min(value = 1 , message = AppConstants.INVALID_FIELD + "price")
    private double price;

    @Min(value = 1 , message = AppConstants.INVALID_FIELD + "borrowPrice")
    private double borrowPrice;

    @Min(value = 1 , message = AppConstants.INVALID_FIELD + "quantity")
    private int quantity;

    @NotBlank(message = AppConstants.INVALID_FIELD + "description")
    private String description;

    @NotBlank(message = AppConstants.INVALID_FIELD + "imageURL")
    private String imageURL;
}
