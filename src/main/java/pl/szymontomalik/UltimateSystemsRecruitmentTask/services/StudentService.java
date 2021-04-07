package pl.szymontomalik.UltimateSystemsRecruitmentTask.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Student;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Teacher;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.repositories.StudentRepository;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.repositories.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public Page<Student> listAll(int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return studentRepository.findAll(pageable);
    }

    public Page<Student> listAllStudentByTeacherId(int pageNum, String sortField, String sortDir, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (keyword != null) {
            List<Teacher> teacherList = teacherRepository.findAll(keyword);
            List<Student> students = new ArrayList<>();
            for (Teacher t : teacherList) {
                Hibernate.initialize(t.getStudents());
                for (Student s : t.getStudents()) {
                    if (!students.contains(s))
                        students.add(s);
                }
            }
            List<Long> idList = new ArrayList<>();
            for (Student s : students) {
                idList.add(s.getId());
            }
            return studentRepository.findByIdIn(idList, pageable);
        }
        return studentRepository.findAll(pageable);
    }

    public void save(Student student) {
        studentRepository.save(student);
    }

    public Student get(Long id) {
        return studentRepository.findById(id).get();
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }


}
