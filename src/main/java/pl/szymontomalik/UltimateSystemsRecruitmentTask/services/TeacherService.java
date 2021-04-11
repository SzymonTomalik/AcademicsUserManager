package pl.szymontomalik.UltimateSystemsRecruitmentTask.services;

import lombok.RequiredArgsConstructor;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public Page<Teacher> pageAll(int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return teacherRepository.findAll(pageable);
    }

    public Page<Teacher> pageAllByStudentId(int pageNum, String sortField, String sortDir, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (keyword != null) {
            List<Student> studentList = studentRepository.findAll(keyword);
            List<Teacher> teachers = new ArrayList<>();
            for (Student s : studentList
            ) {
                Hibernate.initialize(s.getTeachers());
                for (Teacher t : s.getTeachers()
                ) {
                    if (!teachers.contains(t)) {
                        teachers.add(t);
                    }
                }
            }
            List<Long> idList = new ArrayList<>();
            for (Teacher t : teachers) {
                idList.add(t.getId());
            }
            return teacherRepository.findByIdIn(idList, pageable);
        }
        return teacherRepository.findAll(pageable);
    }


    public Page<Teacher> pageAllByKeyword(int pageNum, String sortField, String sortDir, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (keyword != null) {
            return teacherRepository.findAll(keyword, pageable);
        }
        return teacherRepository.findAll(pageable);
    }

    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public Teacher get(Long id) {
        return teacherRepository.findById(id).get();
    }

    public void delete(Long id) {
        Teacher teacher = teacherRepository.getOne(id);
        Hibernate.initialize(teacher.getStudents());
        for (Student s : teacher.getStudents()
        ) {
            Set<Teacher> teachers = s.getTeachers();
            Hibernate.initialize(teachers);
            teachers.remove(teacher);
        }
        teacher.setStudents(null);
        teacherRepository.save(teacher);
        teacherRepository.deleteById(id);
    }
}
