package com.nhnacademy.twojopingfront.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {
    @EmbeddedId
    private CartId id;

    @Column(name = "quantity")
    private int quantity;

    public Book getBook() {
        return id.getBook();
    }

    public Member getMember() {
        return id.getMember();
    }
}
