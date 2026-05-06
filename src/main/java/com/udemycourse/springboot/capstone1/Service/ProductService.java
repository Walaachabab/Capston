package com.udemycourse.springboot.capstone1.Service;

import com.udemycourse.springboot.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    ArrayList<Product> products = new ArrayList<>();

    // Get
    public ArrayList<Product> getAll(){
        return products;
    }

    // Add
    public void addProduct (Product product){
        products.add(product);
    }


    // Update
    public boolean update(String id , Product product){
        for(int i =0 ; i < products.size() ; i++){
            if(products.get(i).getId().equals(id)){
               product.setId(id);
                products.set(i,product);
                      return true;
            }
        }
      return false;
    }


   // Delete
    public boolean delete(String id){
        for (int i =0; i < products.size(); i++){
           if(products.get(i).getId().equals(id)){
               products.remove(i);
               return true;
           }
        }
        return false;
    }


    // endpoint:
    //1 hint
    public ArrayList<Product> getProductsByCategory(String categoryID){
        ArrayList<Product> result = new ArrayList<>();
        for(Product p :products){
            if(p.getCategoryID().equals(categoryID)){
                result.add(p);
            }
        }
        return result;
    }

//1 :Extra endpoint
    public ArrayList<Product>getProductsByPriceRange(double minPrice , double maxPrice){
        ArrayList<Product> result = new ArrayList<>();
        for (Product p: products){
            if(p.getPrice() >= minPrice && p.getPrice()<= maxPrice){
                result.add(p);
            }
        }
        return result;
    }

//2 Extra endpoint : search proudct by name
    public Product getProductByName(String name){
       for (Product p :products){
           if (p.getName().equalsIgnoreCase(name)){
               return p;
           }
       }
        return null;
    }

// 3 Extra endpoint:  products by price
public ArrayList<Product> sortByPrice(){
        ArrayList<Product> sorted = new ArrayList<>(products);
        for (int i= 0; i <sorted.size(); i++){
            for (int j = i+1; j<sorted.size(); j++){
                if(sorted.get(i).getPrice() > sorted.get(j).getPrice()){
                    Product temp = sorted.get(i);
                    sorted.set(i,sorted.get(j));
                    sorted.set(j,temp);
                }
            }
        }
        return sorted;

}

// 11 Extra endpoint : Reducing the price of a product by a certain percentage
    public boolean applyDiscount(String productId, double discountPercentage) {
        if (discountPercentage <= 0 || discountPercentage > 100) {
            return false;
        }
        for (Product p : products) {
            if (p.getId().equals(productId)) {

                double discount = p.getPrice() * discountPercentage / 100;
                p.setPrice(p.getPrice() - discount);

                return true;
            }
        }

        return false;
    }


// 12 Extra endpoint : If the product price is above a certain amount, it qualifies for free shipping.
public boolean isEligibleForFreeShipping(String productId, double minimumAmount) {
    if (minimumAmount <= 0) return false;
    for (Product p : products) {
        if (p.getId().equals(productId)) {
            return p.getPrice() >= minimumAmount;
        }
    }
    return false;
}


// 13 Extra endpoint :

    public boolean updatePriceByCategory(String categoryId, double percentage) {

        if (percentage == 0) return false;

        boolean isUpdated = false;

        for (Product p : products) {

            if (p.getCategoryID().equals(categoryId)) {

                double newPrice = p.getPrice() + (p.getPrice() * percentage / 100);
                p.setPrice(newPrice);

                isUpdated = true;
            }
        }

        return isUpdated;
    }


















}
