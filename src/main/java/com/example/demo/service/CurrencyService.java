package com.example.demo.service;

import com.example.demo.entity.Person;

import java.util.Map;

public interface CurrencyService<T> {

    T createPerson(T object);

    Person getPerson(String userID);

    String getUserCurrency(Map<String, String> requestUser);
}
