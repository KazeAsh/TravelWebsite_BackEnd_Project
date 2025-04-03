package com.d288.backendproject.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customers")

@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="customer_id")
    private Long id;

    @Column(name="address", nullable = false)
    @NonNull
    private String address;

    @Column(name="create_date")
    @CreationTimestamp
    private Date create_date;

    @Nonnull
    @Column(name="customer_first_name", nullable = false)
    private String firstName;

    @Column(name="customer_last_name", nullable = false)
    @Nonnull
    private String lastName;

    @Column(name="last_update")
    @UpdateTimestamp
    private Date last_update;

    @Column(name="phone", nullable = false)
    @Nonnull
    private String phone;

    @Column(name="postal_code", nullable = false)
    @Nonnull
    private String postal_code;

    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    public Customer() {

    }
    public Customer(String firstName, String lastName, String address, String phone, String postal_code, Division divisions, Date create_date){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.postal_code = postal_code;
        this.division = divisions;
        this.create_date = new Date();
    }

    public void add(Cart cart){
        if(cart != null){
            if(carts == null){
                carts = new HashSet<>();
            }
            carts.add(cart);
            cart.setCustomer(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    // **Prevent blank values before saving to DB**
    @PrePersist
    @PreUpdate
    private void validateFields() {
        if (firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be blank.");
        }
        if (lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be blank.");
        }
        if (address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be blank.");
        }
        if (phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be blank.");
        }
        if (postal_code.trim().isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be blank.");
        }
    }

}