package com.udemycourse.springboot.capstone1.Service;

import com.udemycourse.springboot.capstone1.Model.MerchantStock;
import com.udemycourse.springboot.capstone1.Model.Product;
import com.udemycourse.springboot.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    // GET
    public ArrayList<User> getAll() {
        return users;
    }

    // ADD
    public void addUser(User user) {
        users.add(user);
    }

    // UPDATE
    public boolean update(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                user.setId(id);
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean delete(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

   ////////endpoint 12 :  buy Product "important"
   public int buyProduct(String userId, String productId, String merchantId) {
       // Search User
       User user = null;

       for (User u : users) {
           if (u.getId().equals(userId)) {
               user = u;
               break;
           }
       }
       if (user == null) {
           return -1; // User not found
       }
       // Search Product
       Product product = null;

       for (Product p : productService.getAll()) {
           if (p.getId().equals(productId)) {
               product = p;
               break;
           }
       }

       if (product == null) {
           return -2; // Product not found
       }

       // Search Merchant Stock
       MerchantStock stock = null;

       for (MerchantStock ms : merchantStockService.getAll()) {

           if (ms.getProductId().equals(productId)
                   && ms.getMerchantId().equals(merchantId)) {

               stock = ms;
               break;
           }
       }

       if (stock == null) {
           return -3; // Merchant stock not found
       }

       // Check Stock
       if (stock.getStock() <= 0) {
           return -4; // Out of stock
       }

       // Check Balance
       if (user.getBalance() < product.getPrice()) {
           return -5; // Insufficient balance
       }

       // Purchase Process
       stock.setStock(stock.getStock() - 1);
       user.setBalance(user.getBalance() - product.getPrice());
       return 1; // Success
   }


//7 Extra endpoint: get user by role Admin | Coustomer
public ArrayList<User> getByRole(String role){
   ArrayList<User> result = new ArrayList<>();
        for(User u : users){
            if(u.getRole().equalsIgnoreCase(role)){
                result.add(u);
            }
        }
    return result;
}


// 8 Extra endpoint: get user with low balance
    public ArrayList<User> getLowBalance(double amount){
        ArrayList<User> result = new ArrayList<>();

        for(User u : users){
            if(u.getBalance() <= amount){
                result.add(u);
            }
        }

        return result;
    }


// 9 Extra endpoint:
// injection by productService
    // Users who can purchase a specific product by  productId
    public ArrayList<User> canAfford(String productId){
        ArrayList<User> result = new ArrayList<>();
        Product product = null;
        for(Product p : productService.getAll()){
            if(p.getId().equals(productId)){
                product = p;
                break;
            }
        }

        if(product == null) return result;

        for(User u : users){
            if(u.getBalance() >= product.getPrice()){
                result.add(u);
            }
        }

        return result;
    }





  // 10 Extra endpoint : Buying a product in quantity
    public boolean buyProductWithQuantity(String userId, String productId, String merchantId, int quantity) {
        if (quantity <= 0) return false;
        User user = null;
        Product product = null;
        MerchantStock stock = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }

        for (Product p : productService.getAll()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        for (MerchantStock ms : merchantStockService.getAll()) {
            if (ms.getProductId().equals(productId) && ms.getMerchantId().equals(merchantId)) {
                stock = ms;
                break;
            }
        }

        if (user == null || product == null || stock == null) return false;
        if (stock.getStock() < quantity) return false;

        double totalPrice = product.getPrice() * quantity;

        if (user.getBalance() < totalPrice) return false;

        stock.setStock(stock.getStock() - quantity);
        user.setBalance(user.getBalance() - totalPrice);
        return true;

    }







//    public boolean buyProduct(String userId, String productId, String merchantId) {
//        //search user
//        User user = null;
//        for (User u : users) {
//            if (u.getId().equals(userId)){
//                user = u;
//                break;
//            }
//        }
//
//        if (user == null) return false;
//
//        // search Product
//        Product product = null;
//        for (Product p : productService.getAll()) {
//            if (p.getId().equals(productId)) {
//                product = p;
//                break;
//            }
//        }
//
//        if (product == null) return false;
//
//        // search for the merchants stock
//        MerchantStock stock = null;     // injection
//        for (MerchantStock ms : merchantStockService.getAll()) {
//            if (ms.getProductId().equals(productId) &&
//                    ms.getMerchantId().equals(merchantId)) {
//
//                stock = ms;
//                break;
//            }
//        }
//
//        if (stock == null) return false;
//
//        //  check the stock availability
//        if (stock.getStock() <= 0) return false;
//
//        //  check the user's balance
//        if (user.getBalance() < product.getPrice()) return false;
//
//        // Execute the purchase process
//        stock.setStock(stock.getStock() - 1); // Reducing stock
//        user.setBalance(user.getBalance() - product.getPrice()); // Reduce balance
//        return true;
//
//    }






    }
