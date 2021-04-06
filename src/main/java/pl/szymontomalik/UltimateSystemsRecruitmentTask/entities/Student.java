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
public class Student extends AcademicUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fieldOfStudy;
    @ManyToMany
    @JoinTable(name = "students_teachers", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teachers;

    public Student(String firstname, String lastname, int age, String email, Long id, String fieldOfStudy, Set<Teacher> teachers) {
        super(firstname, lastname, age, email);
        this.id = id;
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
        return getAge() == student.getAge() && getFirstname().equals(student.getFirstname()) && getLastname().equals(student.getLastname()) && getEmail().equals(student.getEmail()) && getFieldOfStudy().equals(student.getFieldOfStudy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getAge(), getEmail(), getFieldOfStudy());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                "firstname='" + getFirstname() + '\'' +
                ", lastname='" + getLastname() + '\'' +
                ", age=" + getAge() +
                ", email='" + getEmail() + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +

                '}';
    }
}
