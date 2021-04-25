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

    // nativeQuery로 Entity 사용하지 않은 쿼리를 작성
    // nativeQuery를 사용 안하면 Person에 @Where 조건(deleted=false) 이 포함됨
    @Query(value = "select * from Person person where person.deleted = true", nativeQuery = true)
    List<Person> findPeopleDeleted();

    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday and person.birthday.dayOfBirthday = :dayOfBirthday")
    List<Person> findPeopleBirthdayTomorrow(int monthOfBirthday, int dayOfBirthday);
}
