package com.d288.backendproject.services;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
