package com.example.mycontact.repository;

import com.example.mycontact.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @Transactional
    void crud() {
        Person person = Person.builder()
                .name("kyu")
                .hobby("soccer")
                .build();

        personRepository.save(person);

        List<Person> result = personRepository.findAll();

//        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("kyu");
//        assertThat(result.get(0).getHobby()).isEqualTo("soccer");
    }

    @Test
    @Transactional
    void findByBirthdayBetween() {
        List<Person> result = personRepository.findByMonthOfBirthday(8);

        result.forEach(System.out::println);

        assertThat(result.size()).isEqualTo(2);
    }
}