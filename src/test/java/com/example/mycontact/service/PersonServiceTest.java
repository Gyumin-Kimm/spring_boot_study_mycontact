package com.example.mycontact.service;

import com.example.mycontact.controller.dto.PersonDto;
import com.example.mycontact.domain.Person;
import com.example.mycontact.domain.dto.Birthday;
import com.example.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Test
    void getPeopleByName() {
        when(personRepository.findByName("kyu")) // when -> if
                .thenReturn(Lists.newArrayList(new Person("kyu")));

        List<Person> result = personService.getPeopleByName("kyu");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("kyu");
    }

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("kyu")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("kyu");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    void put(){
        personService.put(mockPersonDto());

        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void modifyIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modifyIfNameIsDifferent(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));

        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("kyu")));

        personService.modify(1L, mockPersonDto());

        verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdated()));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("kyu", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
    }

    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(),"kyu")
                    && equals(person.getHobby(),"programming")
                    && equals(person.getAddress(), "판교")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(), "010-1111-2222");
        }

        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }
    }
}
