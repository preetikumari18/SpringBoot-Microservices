package com.example.demo.service;

import com.example.demo.entity.ExchangeRate;
import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.response.ResponseMessageConstants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService<Person> {

    private LocalDateTime localDateTime;

    private final String API_URL = "https://open.exchangerate-api.com/v6/latest";

    private final PersonRepository personRepository;

    private final RestTemplate restTemplate;

    public CurrencyServiceImpl(PersonRepository personRepository, RestTemplateBuilder restTemplate) {
        this.personRepository = personRepository;
        this.restTemplate = restTemplate.build();
    }

    @Override
    public Person createPerson(Person person) {
        localDateTime = LocalDateTime.now();
        person.setTransactionDate(localDateTime);
        return personRepository.save(person);
    }

    @Override
    public Person getPerson(String userID) {
        return personRepository.findByUserID(userID);
    }

    @Override
    public String getUserCurrency(Map<String, String> requestUser) {
        Person person = personRepository.findByUserID(requestUser.get("userID"));
        if (person != null) {
            double transactionAmount = Double.parseDouble(requestUser.get("amount")) * getExchangeRate(person.getCurrency());
            return String.valueOf(transactionAmount);
        } else {
            return null;
        }

    }

    private double getExchangeRate(String currency) {
        ExchangeRate exchangeRate = restTemplate.getForObject(API_URL, ExchangeRate.class);
        if (exchangeRate.getRates().get(currency) != null && exchangeRate.getRates().get(currency) > 1) {
            return exchangeRate.getRates().get(currency);
        } else {
            return exchangeRate.getRates().get("USD");
        }

    }
}
