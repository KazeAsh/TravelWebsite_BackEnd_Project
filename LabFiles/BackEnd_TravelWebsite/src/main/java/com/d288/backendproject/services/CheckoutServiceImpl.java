package com.d288.backendproject.services;

import com.d288.backendproject.dao.CartItemRepository;
import com.d288.backendproject.dao.CartRepository;
import com.d288.backendproject.dao.CustomerRepository;
import com.d288.backendproject.entities.Cart;
import com.d288.backendproject.entities.CartItem;
import com.d288.backendproject.entities.Customer;
import com.d288.backendproject.entities.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{


    private CustomerRepository customerRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository, CartItemRepository cartItemRepository){
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        //get cart from purchase
        Cart cart = purchase.getCart();


        //create the item
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> {
            item.setCart(cart);
            cart.add(item);
        });

        //set customer to the cart and item
       Customer customer = purchase.getCustomer();
       customer.add(cart);

        //create cart date
        cart.setCreate_date(new Date());

        //check the cart if it is empty or items before saving
        if (cart == null || purchase.getCartItems().isEmpty()) {
            //update the order status to cancel
            cart.setStatus(StatusType.canceled);
            return new PurchaseResponse("Cart cannot be empty!");
        }
        //check the purchase price is not zero or negative before saving
        if (cart.getPackage_price() == null || cart.getPackage_price().compareTo(BigDecimal.ZERO) <= 0) {
            //update the order status to cancel
            cart.setStatus(StatusType.canceled);
            return new PurchaseResponse("Purchase price must be greater than zero!");
        }

        //create status for order
        cart.setStatus(StatusType.ordered);

        //generate tracking order and set to cart
        String orderTrackingNumber = generateTrackingOrder();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        //save cart and flush for the next order
        cartRepository.save(cart);

        //if successful sent tracking number
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateTrackingOrder() {
        //generate random UID number Version-4
        return UUID.randomUUID().toString();
    }
}
