package com.example.mycontact.service;

import com.example.mycontact.controller.dto.PersonDto;
import com.example.mycontact.domain.Person;
import com.example.mycontact.exception.PersonNotFoundException;
import com.example.mycontact.exception.RenameNotPermittedException;
import com.example.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public List<Person> getPeopleByName(String name) {
        return personRepository.findByName(name);
    }

    @Transactional
    public Person getPerson(Long id) {
        return personRepository.findById(id)
                .orElse(null);
    }

    /*
    2. 오늘, 내일이 생일인 친구 목록을 반환하는 API 작성 - 60점
    지인 중에서 오늘이나 내일 생일은 사람들의 목록을 얻는 API를 만들어서 선물을 하고자 합니다. 요구사항은 아래와 같습니다.
    - GET /api/person/birthday-friends
    - API 를 호출하는 날자 기준  오늘, 내일이 생일인 사람들의 목록을 반환한다
    - API 를 호출할 때 PathVariable 이나 Pageable 등의 활용은 필수 사항이 아닙니다.
     */
    public List<Person> getBdaypeople() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        return personRepository.findPeopleBirthdayTomorrow(tomorrow.getMonthValue(), tomorrow.getDayOfMonth());
    }

    @Transactional
    public void put(PersonDto personDto) {
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        if (!person.getName().equals(personDto.getName())) {
            throw new RenameNotPermittedException();
        }

        person.set(personDto);

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, String name) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        person.setName(name);

        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        person.setDeleted(true);

        personRepository.save(person);
    }
}
