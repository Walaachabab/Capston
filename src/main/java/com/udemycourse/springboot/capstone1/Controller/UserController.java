package com.udemycourse.springboot.capstone1.Controller;

import com.udemycourse.springboot.capstone1.Api.ApiResponse;
import com.udemycourse.springboot.capstone1.Model.MerchantStock;
import com.udemycourse.springboot.capstone1.Model.Product;
import com.udemycourse.springboot.capstone1.Model.User;
import com.udemycourse.springboot.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.udemycourse.springboot.capstone1.Service.ProductService;
import com.udemycourse.springboot.capstone1.Service.MerchantStockService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;


    // GET
    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAll());
    }

    // ADD
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        userService.addUser(user);

        return ResponseEntity.status(200)
                .body(new ApiResponse("User added successfully"));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,
                                        @Valid @RequestBody User user,
                                        Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated = userService.update(id, user);

        if (isUpdated) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("User updated successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("User not found"));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {

        boolean isDeleted = userService.delete(id);

        if (isDeleted) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("User deleted successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("User not found"));
    }

    // BUY PRODUCT // must be category, product ,merchant , merchantStock ,user
    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId,
                                        @PathVariable String productId,
                                        @PathVariable String merchantId) {

        int result = userService.buyProduct(userId, productId, merchantId);

        switch (result){

            case -1:
                return ResponseEntity.status(400)
                        .body(new ApiResponse("User not found"));

            case -2:
                return ResponseEntity.status(400)
                        .body(new ApiResponse("Product not found"));

            case -3:
                return ResponseEntity.status(400)
                        .body(new ApiResponse("Merchant stock not found"));

            case -4:
                return ResponseEntity.status(400)
                        .body(new ApiResponse("Product out of stock"));

            case -5:
                return ResponseEntity.status(400)
                        .body(new ApiResponse("Insufficient balance"));

            default:
                return ResponseEntity.status(200)
                        .body(new ApiResponse("Purchase completed successfully"));
        }
    }

    // GET USERS BY ROLE
    @GetMapping("/role/{role}")
    public ResponseEntity<?> getByRole(@PathVariable String role) {
        return ResponseEntity.status(200)
                .body(userService.getByRole(role));
    }

    // GET USERS WITH LOW BALANCE
    @GetMapping("/low-balance/{amount}")
    public ResponseEntity<?> getLowBalance(@PathVariable double amount) {
        return ResponseEntity.status(200)
                .body(userService.getLowBalance(amount));
    }

    // USERS WHO CAN AFFORD PRODUCT
    @GetMapping("/can-afford/{productId}")
    public ResponseEntity<?> canAfford(@PathVariable String productId) {
        return ResponseEntity.status(200)
                .body(userService.canAfford(productId));
    }

    // BUY PRODUCT WITH QUANTITY
    @PutMapping("/buy/{userId}/{productId}/{merchantId}/{quantity}")
    public ResponseEntity<?> buyProductWithQuantity(@PathVariable String userId,
                                                    @PathVariable String productId,
                                                    @PathVariable String merchantId,
                                                    @PathVariable int quantity) {

        // Check User
        boolean userExists = false;

        for (User u : userService.getAll()) {
            if (u.getId().equals(userId)) {
                userExists = true;
                break;
            }
        }

        if (!userExists) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("User not found"));
        }

        // Check Product
        boolean productExists = false;

        for (Product p : productService.getAll()) {
            if (p.getId().equals(productId)) {
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Product not found"));
        }

        // Check Merchant Stock
        boolean merchantStockExists = false;

        for (MerchantStock ms : merchantStockService.getAll()) {
            if (ms.getProductId().equals(productId)
                    && ms.getMerchantId().equals(merchantId)) {

                merchantStockExists = true;
                break;
            }
        }

        if (!merchantStockExists) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Merchant stock not found"));
        }

        // Execute Purchase
        boolean isBought =
                userService.buyProductWithQuantity(userId, productId, merchantId, quantity);

        if (isBought) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Products purchased successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Purchase failed"));
    }
}