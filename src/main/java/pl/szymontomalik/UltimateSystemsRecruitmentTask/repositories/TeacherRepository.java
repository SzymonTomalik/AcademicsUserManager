package pl.szymontomalik.UltimateSystemsRecruitmentTask.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Teacher;

import java.util.List;
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Page<Teacher> findByIdIn(List<Long> idList, Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE CONCAT(t.firstname, ' ', t.lastname) LIKE %?1%")
    List<Teacher> findAll(String keyword);

    @Query("SELECT t FROM Teacher t WHERE CONCAT(t.firstname, ' ', t.lastname) LIKE %?1%")
    Page<Teacher> findAll(String keyword, Pageable pageable);

}
