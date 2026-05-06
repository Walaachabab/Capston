package com.udemycourse.springboot.capstone1.Service;

import com.udemycourse.springboot.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
ArrayList<Category> categories = new ArrayList<>();
    // GET
    public ArrayList<Category> getAll() {
        return categories;
    }

    // ADD
    public void addCategory(Category category) {
        categories.add(category);
    }

    // UPDATE
    public boolean update(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                category.setId(id);
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean delete(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
