package com.example.mycontact.domain;

import com.example.mycontact.controller.dto.PersonDto;
import com.example.mycontact.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Builder
@Where(clause = "deleted = false") // 쿼리사용시 항상 적용되는 조건
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull // @RequiredConstructor
    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String hobby;

    private String address;

    @Valid // @Min, @Max check
    @Embedded
    private Birthday birthday;

    private String job;

//    @ToString.Exclude 클래스 상단에 표시도 가능하지만, 오타 위험이 있음
    private String phoneNumber;

    @ColumnDefault("0") // 0 = false
    private boolean deleted;

    public Person(String name) {
        this.name = name;
    }

    public void set(PersonDto personDto) {

        if (StringUtils.hasLength(personDto.getHobby())) {
            this.setHobby(personDto.getHobby());
        }

        if (StringUtils.hasLength(personDto.getAddress())) {
            this.setAddress(personDto.getAddress());
        }

        if (StringUtils.hasLength(personDto.getJob())) {
            this.setJob(personDto.getJob());
        }

        if (StringUtils.hasLength(personDto.getPhoneNumber())) {
            this.setPhoneNumber(personDto.getPhoneNumber());
        }

        if (personDto.getBirthday() != null) {
            this.setBirthday(Birthday.of(personDto.getBirthday()));
        }
    }

    public Integer getAge() {
        if (this.birthday != null) {
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() + 1;
        } else {
            return null;
        }
    }

    public boolean isBirthdayToday() {
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(), this.birthday.getMonthOfBirthday(), this.birthday.getDayOfBirthday()));
    }
}
