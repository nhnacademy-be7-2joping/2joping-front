package com.nhnacademy.twojopingfront.cart.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
@Setter
public class CartId implements Serializable {

    public CartId(Book book, Member member) {
        this.book = book;
        this.member = member;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
