package pl.szymontomalik.UltimateSystemsRecruitmentTask.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Student;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Teacher;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.model.AcademicUser;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageSortAndViewService {
    private final TeacherService teacherService;
    private final StudentService studentService;

    public String selectUserListForTeacherPage(Model model, int pageNum, String sortField, String sortDir, String keyword, boolean flag) {
        if (!flag) {
            pageStudentsByTeacherIdWithKeyword(model, pageNum, sortField, sortDir, keyword);
            return "studentsByTeacherId";
        } else {
            if (keyword != null) {
                pageTeachersByKeyword(model, pageNum, sortField, sortDir, keyword);
            } else {
                pageAllTeachers(model, pageNum, sortField, sortDir);
            }
            return "teachers";
        }
    }

    private void pageAllTeachers(Model model, int pageNum, String sortField, String sortDir) {
        Page<Teacher> page = teacherService.pageAll(pageNum, sortField, sortDir);
        setViewAttributes(model, pageNum, sortField, sortDir, page, null, true);
    }

    private void pageTeachersByKeyword(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Teacher> page = teacherService.pageAllByKeyword(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword, true);
    }

    private void pageStudentsByTeacherIdWithKeyword(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Student> page = studentService.pageAllByTeacherIdWithKeyword(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword, false);
    }

    public String selectUserListForStudentPage(Model model, int pageNum, String sortField, String sortDir, String keyword, boolean flag) {
        if (!flag) {
           pageTeachersByStudentIdWithKeyword(model, pageNum, sortField, sortDir, keyword);
            return "teachersByStudentId";
        } else {
            if (keyword != null) {
                pageStudentsByKeyword(model, pageNum, sortField, sortDir, keyword);
            } else {
                pageAllStudents(model, pageNum, sortField, sortDir);
            }
            return "students";
        }
    }

    private void pageAllStudents(Model model, int pageNum, String sortField, String sortDir) {
        Page<Student> page = studentService.pageAll(pageNum, sortField, sortDir);
        setViewAttributes(model, pageNum, sortField, sortDir, page, null, true);

    }

    private void pageStudentsByKeyword(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Student> page = studentService.pageAllByKeyword(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword, true);
    }

    public void pageTeachersByStudentIdWithKeyword(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Teacher> page = teacherService.pageAllByStudentIdWithKeyword(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword, false);
    }

    private void setViewAttributes(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField, @Param("sortDir") String sortDir, Page<? extends AcademicUser> page, @Param("keyword") String keyword, @Param("flag") boolean flag) {
        List<? extends AcademicUser> userList = page.getContent();
        model.addAttribute("userList", userList);
        setSorting(model, pageNum, sortField, sortDir, page.getTotalPages(), page.getTotalElements(), keyword, flag);
    }

    private void setSorting(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField, @Param("sortDir") String sortDir, int totalPages, long totalElements, @Param("keyword") String keyword, @Param("flag") boolean flag) {
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalElements);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("desc") ? "asc" : "desc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("flag", flag);

    }

    public void pageTeachersByStudentId(Model model, int pageNum, String sortField, String sortDir, Long studentId) {
        Page<Teacher> page = teacherService.pageAllByStudentId(studentId, pageNum, sortField, sortDir);
        setViewAttributes(model, pageNum, sortField, sortDir, page, null, false);
    }

    public void pageStudentsByTeacherId(Model model, int pageNum, String sortField, String sortDir, Long teacherId) {
        Page<Student> page= studentService.pageAllByTeacherId(teacherId,pageNum,sortField,sortDir);
        setViewAttributes(model, pageNum, sortField, sortDir, page, null, false);
    }
}
