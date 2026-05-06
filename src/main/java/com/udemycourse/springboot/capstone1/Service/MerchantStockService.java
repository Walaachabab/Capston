package com.udemycourse.springboot.capstone1.Service;


import com.udemycourse.springboot.capstone1.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    //get
    public ArrayList<MerchantStock> getAll() {
        return merchantStocks;
    }

    // add
public void addMerchantStock(MerchantStock merchantStock){
        merchantStocks.add(merchantStock);
}

    // update
 public boolean update(String id ,MerchantStock merchantStock){
        for (int i= 0 ; i<merchantStocks.size() ; i++){
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStock.setId(id);
                merchantStocks.set(i,merchantStock);
                return true;
            }
        }
        return false;
 }


 // delete
    public boolean delete(String id){
       for (int i=0; i<merchantStocks.size();i++){
           if(merchantStocks.get(i).getId().equals(id)){
               merchantStocks.remove(i);
               return true;
           }
       }
        return false;
    }


    // add stock : endpoint: 11
    public boolean addStock(String productId, String merchantId, int amount) {
        for (MerchantStock ms : merchantStocks) {
            if (ms.getProductId().equals(productId)
                    && ms.getMerchantId().equals(merchantId)) {
                ms.setStock(ms.getStock() + amount);
                return true;
            }
        }
        return false;
    }

 // 4 Extra endpoint: get products by merchant

    public ArrayList<MerchantStock> getByMerchant(String merchantId){
            ArrayList<MerchantStock> result = new ArrayList<>();
            for(MerchantStock ms : merchantStocks){
               if(ms.getMerchantId().equals(merchantId)){
                   result.add(ms);
               }
            }
            return result;

    }


 // 5 Extra endpoint : get low stock products
    public ArrayList<MerchantStock> getLowStock(int limit) {
        ArrayList<MerchantStock> result = new ArrayList<>();
        for (MerchantStock ms : merchantStocks) {
            if (ms.getStock() <= limit) {
                result.add(ms);
            }
        }
        return result;
    }


 //6 Extra endpoint: get out of stock
    public ArrayList<MerchantStock> getOutOfStock() {
        ArrayList<MerchantStock> result = new ArrayList<>();
        for (MerchantStock ms : merchantStocks) {
            if (ms.getStock() == 0) {
                result.add(ms);
            }
        }
        return result;
    }







}
