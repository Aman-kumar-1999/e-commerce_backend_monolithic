package com.eqipped.service.impl;

import com.eqipped.entities.Cart;
import com.eqipped.entities.Order;
import com.eqipped.repositories.CartRepo;
import com.eqipped.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CartRepo cartRepo;

    @Override
    public List<Cart> getAllCart() {
        List<Cart> li = cartRepo.findAll();
        return li;
    }

    @Override
    public Cart getCart(String orderId) {
        Cart cart = cartRepo.findById(orderId).get();
        return cart;
    }

    @Override
    public Cart createCart(Cart cart) {
        cart.setDate(LocalDate.now());
        Cart cart1 = cartRepo.save(cart);
        return cart1;
    }

    @Override
    public Cart updateCart(Cart cart) {
        Cart oldCart = cartRepo.findById(cart.getOrderId()).get();
        Cart newCart = new Cart();
        if(oldCart != null){
            oldCart = cart;
            oldCart.setDate(LocalDate.now());
            newCart = cartRepo.save(oldCart);
        }
        return newCart;
    }

    @Override
    public void deleteCart(String orderId) {
        cartRepo.deleteById(orderId);
    }

    @Override
    public void deleteCartOfEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        mongoTemplate.findAllAndRemove(query, Cart.class );

    }
    @Override
    public List<Cart> findCartOfEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Cart> carts = mongoTemplate.find(query, Cart.class);
        return carts;
    }
}
