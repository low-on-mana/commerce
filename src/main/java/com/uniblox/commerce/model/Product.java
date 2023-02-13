package com.uniblox.commerce.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Double price;
}