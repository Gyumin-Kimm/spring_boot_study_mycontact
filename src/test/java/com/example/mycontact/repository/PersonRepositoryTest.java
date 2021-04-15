package com.example.mycontact.repository;

import com.example.mycontact.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud() {
        Person person = new Person();
        person.setName("john");

        personRepository.save(person);

        List<Person> result = personRepository.findByName("john");

        System.out.println(result.size());
        System.out.println(result.get(0).getName());
    }

    @Test
    void findByBirthdayBetween() {
        List<Person> result = personRepository.findByMonthOfBirthday(8);

        System.out.println(result.size());
        System.out.println(result.get(0).getName());
        System.out.println(result.get(1).getName());
    }
}