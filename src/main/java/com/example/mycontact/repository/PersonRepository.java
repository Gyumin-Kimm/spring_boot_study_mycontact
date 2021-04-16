package com.example.mycontact.repository;

import com.example.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// @Repository  JpaRepository 상속받은 경우에는 자동
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);

    // JPQL
    // ?1 = 첫번째 인자를 뜻함(monthOfBirthday)
//    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = ?1")
//    List<Person> findByMonthOfBirthday(int monthOfBirthday);
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday")
    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday);

    @Query(value = "select * from Person person where person.deleted = true", nativeQuery = true)
    List<Person> findPeopleDeleted();
}
