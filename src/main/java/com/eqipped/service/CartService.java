package com.eqipped.service;

import com.eqipped.entities.Cart;

import java.util.List;

public interface CartService {

    // get all carts
    public List<Cart> getAllCart();

    // get cart by orderId
    public Cart getCart(String orderId);

    // create Cart
    public  Cart createCart(Cart cart);

    // update Cart
    public Cart updateCart(Cart cart);

    // delete cart
    public void deleteCart(String orderId);

    // delete cart of with email
    public void deleteCartOfEmail(String email);

    // find Cart of email
    public List<Cart> findCartOfEmail(String email);

}
