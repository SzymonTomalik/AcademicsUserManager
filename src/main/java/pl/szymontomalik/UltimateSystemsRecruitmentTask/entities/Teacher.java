package pl.szymontomalik.UltimateSystemsRecruitmentTask.entities;

import lombok.Getter;
import lombok.Setter;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.model.AcademicUser;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Teacher extends AcademicUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String majoringIn;
    @ManyToMany (mappedBy = "teachers")
    private Set<Student> students;

    public Teacher() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return getAge() == teacher.getAge() && getFirstname().equals(teacher.getFirstname()) && getLastname().equals(teacher.getLastname()) && getEmail().equals(teacher.getEmail()) && getMajoringIn().equals(teacher.getMajoringIn());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getAge(), getEmail(), getMajoringIn());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstname='" + getFirstname() + '\'' +
                ", lastname='" + getLastname() + '\'' +
                ", age=" + getAge() +
                ", email='" + getEmail() + '\'' +
                ", majoringIn='" + majoringIn + '\'' +
                '}';
    }
}