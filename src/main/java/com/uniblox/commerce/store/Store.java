package com.uniblox.commerce.store;

import com.uniblox.commerce.model.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Store {

    /**
     * @return Simple cart bean for demo purposes
     */
    @Bean
    public Cart createCart() {
        return new Cart();
    }
}
