package com.craftbay.crafts.service.impl;

import com.craftbay.crafts.dto.cart.AddToCartRequestDto;
import com.craftbay.crafts.dto.cart.CartItemRateRequestDto;
import com.craftbay.crafts.dto.cart.CartItemRequestDto;
import com.craftbay.crafts.dto.cart.CartRequestDto;
import com.craftbay.crafts.dto.cart.response.CartResponseDto;
import com.craftbay.crafts.entity.cart.Cart;
import com.craftbay.crafts.entity.cart.CartItem;
import com.craftbay.crafts.entity.product.Product;
import com.craftbay.crafts.entity.user.User;
import com.craftbay.crafts.repository.CartItemRepository;
import com.craftbay.crafts.repository.CartRepository;
import com.craftbay.crafts.repository.ProductRepository;
import com.craftbay.crafts.repository.UserRepository;
import com.craftbay.crafts.service.CartService;
import com.craftbay.crafts.util.CartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public String addToCart(AddToCartRequestDto request) throws Exception {

        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (optionalUser.isPresent()) {
            Optional<Cart> optinalExistingCart = cartRepository.findByUserAndIsOrdered(optionalUser.get(), Boolean.FALSE);
            Optional<Product> optionalProduct = productRepository.findById(request.getProductId());

            if (optinalExistingCart.isPresent()) {
                Cart existingCart = optinalExistingCart.get();
                if (optionalProduct.isPresent()){

                    Optional<CartItem> optionalItem = existingCart.getCartItems().stream().filter(x->x.getProduct() == optionalProduct.get()).findFirst();
                    if (optionalItem.isPresent()) {
                        CartItem cartItem = optionalItem.get();
                        if (cartItem.getProduct().getRemainingQuantity()<(cartItem.getQuantity() + request.getQuantity())) {
                            throw new Exception("Cart Exceeded the maximum amount of this product!");
                        } else {
                            optionalItem.get().setQuantity(optionalItem.get().getQuantity() + request.getQuantity());
                        }

                    } else {
                        CartItem newItem = new CartItem();
                        newItem.setProduct(optionalProduct.get());
                        newItem.setQuantity(request.getQuantity());
                        existingCart.getCartItems().add(newItem);
                    }

                    cartRepository.save(existingCart);
                } else {
                    throw new Exception("Product Not Found");
                }
            } else {
                Cart newCart = new Cart();
                List<CartItem> cartItems = new ArrayList<>();
                CartItem item = new CartItem();
                item.setProduct(optionalProduct.get());
                item.setQuantity(request.getQuantity());
                cartItems.add(item);

                newCart.setOrdered(Boolean.FALSE);
                newCart.setUser(optionalUser.get());
                newCart.setCartItems(cartItems);

                cartRepository.save(newCart);
            }
        } else {
            throw new Exception("User Not Found");
        }

        return null;
    }

    @Override
    public String updateCart(CartRequestDto request) throws Exception {

        Optional<Cart> optionalExistingCart = cartRepository.findByIdAndIsOrdered(request.getId(), Boolean.FALSE);
        if (optionalExistingCart.isPresent()) {
            Cart existingCart = optionalExistingCart.get();

            for (int i=0;i<existingCart.getCartItems().size();i++) {
                CartItem existingCartItem = existingCart.getCartItems().get(i);
                Optional<CartItemRequestDto> optionalItem = request.getCartItems().stream().filter(x -> x.getProductId() == existingCartItem.getProduct().getId()).findFirst();
                if (optionalItem.isPresent()) {
                    existingCartItem.setQuantity(optionalItem.get().getQuantity());
                } else {
                    cartItemRepository.deleteById(existingCartItem.getId());
                    existingCart.getCartItems().remove(i);
                }
            }
            cartRepository.save(existingCart);
        } else {
            throw new Exception("Cart Not Found");
        }
        return null;
    }

    @Override
    public CartResponseDto findCartByUser(int userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Cart> cart = cartRepository.findCartByUserAndIsOrdered(user, Boolean.FALSE);
            if (cart.isPresent()) {
                return CartUtil.covertCartToCartResponseDto(cart.get());
            } else {
                throw new Exception("No Cart for this user");
            }
        } else {
            throw new Exception("User Not Found!");
        }
    }

    @Override
    public void rateCartItem(CartItemRateRequestDto request) throws Exception {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(request.getCartItemId());
        CartItem cartItem = null;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            Product ratedProduct = cartItem.getProduct();

            if (cartItem.getRate()==0) {
                ratedProduct.setRate(ratedProduct.getRate() + request.getRate());
                ratedProduct.setNoOfRatings(ratedProduct.getNoOfRatings()+1);
            } else {
                ratedProduct.setRate(ratedProduct.getRate() - cartItem.getRate() + request.getRate());
            }
            productRepository.save(ratedProduct);
            cartItem.setRate(request.getRate());
            cartItemRepository.save(cartItem);
        } else {
            throw new Exception("Cart Item Not Found!");
        }
    }

}
