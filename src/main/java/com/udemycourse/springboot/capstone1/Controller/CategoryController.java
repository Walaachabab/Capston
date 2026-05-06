package com.udemycourse.springboot.capstone1.Controller;

import com.udemycourse.springboot.capstone1.Api.ApiResponse;
import com.udemycourse.springboot.capstone1.Model.Category;
import com.udemycourse.springboot.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // GET
    @GetMapping("/get")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.status(200).body(categoryService.getAll());
    }

    // ADD
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        categoryService.addCategory(category);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Category added successfully"));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id,
                                            @Valid @RequestBody Category category,
                                            Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated = categoryService.update(id, category);

        if (isUpdated) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Category updated successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Category not found"));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {

        boolean isDeleted = categoryService.delete(id);

        if (isDeleted) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("Category deleted successfully"));
        }

        return ResponseEntity.status(400)
                .body(new ApiResponse("Category not found"));
    }
}
