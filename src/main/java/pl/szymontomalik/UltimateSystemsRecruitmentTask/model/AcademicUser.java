package pl.szymontomalik.UltimateSystemsRecruitmentTask.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
public class AcademicUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2)
    private String firstname;
    private String lastname;
    @Min(19)
    private int age;
    @Email
    @Column(unique = true)
    private String email;

    public AcademicUser(Long id, String firstname, String lastname, int age, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
    }

    public AcademicUser() {
    }

    @Override
    public String toString() {
        return "AcademicUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcademicUser)) return false;
        AcademicUser that = (AcademicUser) o;
        return getAge() == that.getAge() && getId().equals(that.getId()) && getFirstname().equals(that.getFirstname()) && getLastname().equals(that.getLastname()) && getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getLastname(), getAge(), getEmail());
    }
}
