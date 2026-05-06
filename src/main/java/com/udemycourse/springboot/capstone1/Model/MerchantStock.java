package com.udemycourse.springboot.capstone1.Model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

  @NotEmpty(message = "ID cannot be empty")
   private String id ;

  @NotEmpty(message = "Productid cannot be empty")
   private String productId;

  @NotEmpty(message = "Merchantid cannot be empty")
   private String merchantId;

  @NotNull(message = "Stock cannot be Null")
  @Min(value = 11 , message = "Stock must be more than 10")
   private int stock;



}
