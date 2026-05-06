package com.udemycourse.springboot.capstone1.Controller;

import com.udemycourse.springboot.capstone1.Api.ApiResponse;
import com.udemycourse.springboot.capstone1.Model.Merchant;
import com.udemycourse.springboot.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    // GET
    @GetMapping("/get")
    public ResponseEntity<?> getAllMerchants() {
        return ResponseEntity.status(200).body(merchantService.getAll());
    }

    // ADD
    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        merchantService.addMerchant(merchant);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Merchant added successfully"));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id,
                                            @Valid @RequestBody Merchant merchant,
                                            Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated = merchantService.update(id, merchant);

        if (isUpdated) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Merchant updated successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Merchant not found"));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {

        boolean isDeleted = merchantService.delete(id);

        if (isDeleted) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Merchant deleted successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Merchant not found"));

    }
}