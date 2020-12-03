package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.service.CurrencyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/currencyUser")
public class CurrencyConverterController {

    private final CurrencyServiceImpl currencyService;

    /*@PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody Person person){
        return "User Created";
    }*/

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Person person) {
        Person personCreated = currencyService.createPerson(person);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Person> getUser(@PathVariable("id") String userId) {
        Person person = currencyService.getPerson(userId);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/getAmount")
    public ResponseEntity<String> getConvertedAmount(@RequestParam Map<String, String> requestUser) {
        String amount = currencyService.getUserCurrency(requestUser);
        if (amount != null) {
            return new ResponseEntity<>(currencyService.getUserCurrency(requestUser), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /*@PutMapping("/update")
    public ResponseEntity<Person> updateUSer(@RequestBody Person person){
        return new ResponseEntity<>(currencyService.getUserCurrency(""), HttpStatus.OK);
    }*/
}
