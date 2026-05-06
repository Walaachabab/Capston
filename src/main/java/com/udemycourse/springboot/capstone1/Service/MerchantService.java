package com.udemycourse.springboot.capstone1.Service;


import com.udemycourse.springboot.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();
    // GET
    public ArrayList<Merchant> getAll() {
        return merchants;
    }

    // ADD
    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    // UPDATE
    public boolean update(String id, Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchant.setId(id);
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean delete(String id) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }




}
