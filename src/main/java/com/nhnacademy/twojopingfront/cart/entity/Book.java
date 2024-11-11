package com.nhnacademy.twojopingfront.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @Column(nullable = false, length = 13, unique = true)
    private String isbn;

    @Column(nullable = false)
    private int retailPrice;

    @Column(nullable = false)
    private int sellingPrice;

    @Column(nullable = false)
    private boolean giftWrappable;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false, columnDefinition = "INT default 0")
    private int remainQuantity;

    @Column(nullable = false, columnDefinition = "INT default 0")
    private int views;

    @Column(nullable = false, columnDefinition = "INT default 0")
    private int likes;

}
