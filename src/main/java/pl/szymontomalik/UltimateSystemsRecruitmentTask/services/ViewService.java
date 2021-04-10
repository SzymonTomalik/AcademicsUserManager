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
public class ViewService {
    private final TeacherService teacherService;
    private final StudentService studentService;

    public String selectUserListForTeacherPage(Model model, int pageNum, String sortField, String sortDir, String keyword, boolean flag) {
        if (!flag) {
            return pageStudentsByTeacherId(model, pageNum, sortField, sortDir, keyword);
        } else {
            if (keyword != null) {
                return pageTeachersByKeyword(model, pageNum, sortField, sortDir, keyword);
            } else {
                return pageAllTeachers(model, pageNum, sortField, sortDir);
            }
        }
    }

    private String pageAllTeachers(Model model, int pageNum, String sortField, String sortDir) {
        Page<Teacher> page = teacherService.pageAll(pageNum, sortField, sortDir);
        setViewAttributes(model, pageNum, sortField, sortDir, page, null, true);
        return "teachers";
    }

    private String pageTeachersByKeyword(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Teacher> page = teacherService.pageAllByKeyword(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword, true);
        return "teachers";
    }

    private String pageStudentsByTeacherId(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Student> page = studentService.pageAllByTeacherId(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword, false);
        return "studentsByTeacherId";
    }

    public String selectUserListForStudentPage(Model model, int pageNum, String sortField, String sortDir, String keyword, boolean flag) {
        if (!flag) {
            return pageTeachersByStudentId(model, pageNum, sortField, sortDir, keyword);
        } else {
            if (keyword != null) {
                return pageStudentsByKeyword(model, pageNum, sortField, sortDir, keyword);
            } else {
                return pageAllStudents(model, pageNum, sortField, sortDir);
            }
        }
    }

    private String pageAllStudents(Model model, int pageNum, String sortField, String sortDir) {
        Page<Student> page = studentService.pageAll(pageNum, sortField, sortDir);
        setViewAttributes(model, pageNum, sortField, sortDir, page, null, true);
        return "students";
    }

    private String pageStudentsByKeyword(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Student> page = studentService.pageAllByKeyword(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword, true);
        return "students";
    }

    private String pageTeachersByStudentId(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Teacher> page = teacherService.pageAllByStudentId(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword, false);
        return "teachersByStudentId";
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
}
