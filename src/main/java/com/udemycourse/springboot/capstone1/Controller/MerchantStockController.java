package com.udemycourse.springboot.capstone1.Controller;

import com.udemycourse.springboot.capstone1.Api.ApiResponse;
import com.udemycourse.springboot.capstone1.Model.MerchantStock;
import com.udemycourse.springboot.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant-stocks")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    // GET
    @GetMapping("/get")
    public ResponseEntity<?> getAllMerchantStocks() {
        return ResponseEntity.status(200).body(merchantStockService.getAll());
    }

    // ADD
    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@Valid @RequestBody MerchantStock merchantStock,
                                              Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        merchantStockService.addMerchantStock(merchantStock);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Merchant stock added successfully"));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id,
                                                 @Valid @RequestBody MerchantStock merchantStock,
                                                 Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated = merchantStockService.update(id, merchantStock);

        if (isUpdated) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Merchant stock updated successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Merchant stock not found"));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id) {

        boolean isDeleted = merchantStockService.delete(id);

        if (isDeleted) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Merchant stock deleted successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Merchant stock not found"));
    }

    // ADD STOCK
    @PutMapping("/add-stock/{productId}/{merchantId}/{amount}")
    public ResponseEntity<?> addStock(@PathVariable String productId,
                                      @PathVariable String merchantId,
                                      @PathVariable int amount) {

        boolean isAdded = merchantStockService.addStock(productId, merchantId, amount);

        if (isAdded) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Stock added successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Merchant stock not found"));
    }

    // GET BY MERCHANT
    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<?> getByMerchant(@PathVariable String merchantId) {
        return ResponseEntity.status(200)
                .body(merchantStockService.getByMerchant(merchantId));
    }

    // GET LOW STOCK
    @GetMapping("/low-stock/{limit}")
    public ResponseEntity<?> getLowStock(@PathVariable int limit) {
        ArrayList<MerchantStock> result = merchantStockService.getLowStock(limit);
        if(result.isEmpty()){
            return ResponseEntity.status(200).body(new ApiResponse("All products have stock above the limit"));
        }
       return ResponseEntity.status(200).body(result);

    }

    // GET OUT OF STOCK
    @GetMapping("/out-of-stock")
    public ResponseEntity<?> getOutOfStock() {
        ArrayList<MerchantStock> result = merchantStockService.getOutOfStock();

        if(result.isEmpty()){
            return ResponseEntity.status(200).body(new ApiResponse("No products are out of stock"));
        }
        return ResponseEntity.status(200)
                .body(result);
    }
}