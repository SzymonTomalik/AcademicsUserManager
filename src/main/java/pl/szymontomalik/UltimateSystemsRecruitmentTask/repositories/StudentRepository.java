package pl.szymontomalik.UltimateSystemsRecruitmentTask.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Student;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByIdIn(List<Long> idList, Pageable pageable);

}