package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private String userID;
    private String name;
    private int age;
    private String country;
    private String currency;
    private LocalDateTime transactionDate;



}
