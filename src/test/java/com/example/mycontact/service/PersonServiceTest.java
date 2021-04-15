package com.example.mycontact.service;

import com.example.mycontact.domain.Person;
import com.example.mycontact.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Test
    void getPeopleByName() {
        List<Person> result = personService.getPeopleByName("kyu");

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("kyu");
    }

    @Test
    void getPerson() {
        Person person = personService.getPerson(3L);

        Assertions.assertThat(person.getName()).isEqualTo("dennis");
    }

}