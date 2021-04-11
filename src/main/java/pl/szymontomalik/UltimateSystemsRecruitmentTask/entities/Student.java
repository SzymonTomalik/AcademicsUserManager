package pl.szymontomalik.UltimateSystemsRecruitmentTask.entities;

import lombok.Getter;
import lombok.Setter;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.model.AcademicUser;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Student extends AcademicUser {

    @NotBlank(message = "{NotBlank.Message}")
    private String fieldOfStudy;
    @ManyToMany(mappedBy = "students")
    private Set<Teacher> teachers;

    public Student(Long id, String firstname, String lastname, int age, String email, String fieldOfStudy, Set<Teacher> teachers) {
        super(id, firstname, lastname, age, email);
        this.fieldOfStudy = fieldOfStudy;
        this.teachers = teachers;
    }

    public Student() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getId().equals(student.getId()) && getAge() == student.getAge() && getFirstname().equals(student.getFirstname()) && getLastname().equals(student.getLastname()) && getEmail().equals(student.getEmail()) && getFieldOfStudy().equals(student.getFieldOfStudy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getLastname(), getAge(), getEmail(), getFieldOfStudy());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() + '\'' +
                "firstname='" + getFirstname() + '\'' +
                ", lastname='" + getLastname() + '\'' +
                ", age=" + getAge() +
                ", email='" + getEmail() + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                '}';
    }
}
