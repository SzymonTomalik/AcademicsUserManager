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

    public String selectUserList(Model model, int pageNum, String sortField, String sortDir, String keyword, boolean flag) {
        if (!flag) {
            if (keyword != null) {
                return pageStudentsByTeacherId(model, pageNum, sortField, sortDir, keyword);
            } else {
                return pageAllTeachers(model, pageNum, sortField, sortDir);
            }
        } else {
            return pageTeachersByKeyword(model, pageNum, sortField, sortDir, keyword);
        }

    }

    private String pageAllTeachers(Model model, int pageNum, String sortField, String sortDir) {
        Page<Teacher> page = teacherService.listAll(pageNum, sortField, sortDir);
        setViewAttributes(model, pageNum, sortField, sortDir, page, null);
        return "teachers";
    }
    private String pageTeachersByKeyword(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Teacher> page = teacherService.listAllTeachersByKeyword(pageNum,sortField,sortDir,keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword);
        return "teachers";
    }

    private String pageStudentsByTeacherId(Model model, int pageNum, String sortField, String sortDir, String keyword) {
        Page<Student> page = studentService.listAllStudentByTeacherId(pageNum, sortField, sortDir, keyword);
        setViewAttributes(model, pageNum, sortField, sortDir, page, keyword);
        model.addAttribute("keyword", keyword);
        return "studentsByTeacherId";
    }

    public void setViewAttributes(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField, @Param("sortDir") String sortDir, Page<? extends AcademicUser> page, @Param("keyword") String keyword) {
        List<? extends AcademicUser> userList = page.getContent();
        model.addAttribute("userList", userList);
        setSorting(model, pageNum, sortField, sortDir, page.getTotalPages(), page.getTotalElements(), keyword);
    }

    public void setSorting(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField, @Param("sortDir") String sortDir, int totalPages, long totalElements, @Param("keyword") String keyword) {
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalElements);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
    }
}
