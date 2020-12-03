package com.example.demo.repository;

import com.example.demo.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryIntegrationTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PersonRepository personRepository;

    @Test
    public void whenFindByName_thenReturnPerson(){

        //given
        Person expectedResult = Person.builder()
                .name("Preeti")
                .age(27)
                .userID("preeti18")
                .country("canada")
                .currency("CAD")
                .transactionDate(LocalDateTime.now())
                .build();

        testEntityManager.persist(expectedResult);
        testEntityManager.flush();

        //when
        Person actualResult = personRepository.findByUserID("preeti18");

        //then
        assertThat(actualResult.getName()).isEqualTo(expectedResult.getName());

    }

    @Test
    public void whenNotFound_thenReturnNull(){

        //given
        Person expectedResult = Person.builder()
                .name("Preeti")
                .age(27)
                .userID("preeti18")
                .country("canada")
                .currency("CAD")
                .transactionDate(LocalDateTime.now())
                .build();

        testEntityManager.persist(expectedResult);
        testEntityManager.flush();

        //when
        Person actualResult = personRepository.findByUserID("tarun03");

        //then
        assertThat(actualResult).isNull();

    }
}
