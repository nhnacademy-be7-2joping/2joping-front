package com.nhnacademy.twojopingfront.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    @NotNull
    @Size(max = 20)
    private String name;

    @NotNull
    @Column(unique = true, nullable = false, length = 20)
    private String phone;

    @NotNull
    @Email
    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @OneToOne(mappedBy = "customer")
    @JsonIgnore
    private Member member;
}
