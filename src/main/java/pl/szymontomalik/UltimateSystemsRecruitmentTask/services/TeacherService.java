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

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public Page<Teacher> listAll(int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum-1, 5, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return teacherRepository.findAll(pageable);
    }
    public Page<Teacher> listAllTeacherByStudentId(Long studentId, int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum-1, 5, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        Student student= studentRepository.findById(studentId).get();
        Hibernate.initialize(student.getTeachers());
        List<Teacher> teachers = new ArrayList<>(List.copyOf(student.getTeachers()));
        List<Long> idList = new ArrayList<>();
        for (Teacher t:teachers) {
            idList.add(t.getId());
        }
        return teacherRepository.findByIdIn(idList,pageable);
    }
    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }
    public Teacher get(Long id) {
        return teacherRepository.findById(id).get();
    }
    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }
}
