package com.udemycourse.springboot.capstone1.Controller;

import com.udemycourse.springboot.capstone1.Api.ApiResponse;
import com.udemycourse.springboot.capstone1.Model.Product;
import com.udemycourse.springboot.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // GET
    @GetMapping("/get")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.status(200).body(productService.getAll());
    }

    // ADD
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        productService.addProduct(product);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Product added successfully"));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id,
                                           @Valid @RequestBody Product product,
                                           Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated = productService.update(id, product);

        if (isUpdated) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Product updated successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Product not found"));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {

        boolean isDeleted = productService.delete(id);

        if (isDeleted) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Product deleted successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Product not found"));
    }

    // GET PRODUCTS BY CATEGORY
    @GetMapping("/category/{categoryID}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String categoryID) {
        return ResponseEntity.status(200)
                .body(productService.getProductsByCategory(categoryID));
    }

    // GET PRODUCTS BY PRICE RANGE
    @GetMapping("/price-range")
    public ResponseEntity<?> getProductsByPriceRange(@RequestParam double minPrice,
                                                     @RequestParam double maxPrice) {
        return ResponseEntity.status(200)
                .body(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    // SEARCH PRODUCT BY NAME
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {

        Product product = productService.getProductByName(name);

        if (product != null) {
            return ResponseEntity.status(200).body(product);
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Product not found"));
    }

    // SORT PRODUCTS BY PRICE
    @GetMapping("/sort-by-price")
    public ResponseEntity<?> sortByPrice() {
        return ResponseEntity.status(200)
                .body(productService.sortByPrice());
    }

    // APPLY DISCOUNT
    @PutMapping("/discount/{productId}/{discountPercentage}")
    public ResponseEntity<?> applyDiscount(@PathVariable String productId,
                                           @PathVariable double discountPercentage) {

        boolean isDiscounted = productService.applyDiscount(productId, discountPercentage);

        if (isDiscounted) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Discount applied successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Failed to apply discount"));
    }

    // CHECK FREE SHIPPING
    @GetMapping("/free-shipping/{productId}/{minimumAmount}")
    public ResponseEntity<?> isEligibleForFreeShipping(@PathVariable String productId,
                                                       @PathVariable double minimumAmount) {

        boolean isEligible = productService.isEligibleForFreeShipping(productId, minimumAmount);

        if (isEligible) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Product is eligible for free shipping"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Product is not eligible for free shipping"));
    }

    // UPDATE PRICE BY CATEGORY
    @PutMapping("/category/price/{categoryId}/{percentage}")
    public ResponseEntity<?> updatePriceByCategory(@PathVariable String categoryId,
                                                   @PathVariable double percentage) {

        boolean isUpdated = productService.updatePriceByCategory(categoryId, percentage);

        if (isUpdated) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Category prices updated successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Failed to update category prices"));
    }
}
