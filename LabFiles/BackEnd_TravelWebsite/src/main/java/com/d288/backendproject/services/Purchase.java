package com.d288.backendproject.services;

import com.d288.backendproject.entities.Cart;
import com.d288.backendproject.entities.CartItem;
import com.d288.backendproject.entities.Customer;
import lombok.Data;


import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;

}
