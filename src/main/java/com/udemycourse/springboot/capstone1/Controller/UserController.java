package com.udemycourse.springboot.capstone1.Controller;

import com.udemycourse.springboot.capstone1.Api.ApiResponse;
import com.udemycourse.springboot.capstone1.Model.User;
import com.udemycourse.springboot.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

        boolean isBought = userService.buyProduct(userId, productId, merchantId);

        if (isBought) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Product purchased successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Purchase failed"));
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

        boolean isBought = userService.buyProductWithQuantity(userId, productId, merchantId, quantity);

        if (isBought) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Products purchased successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Purchase failed"));
    }
}