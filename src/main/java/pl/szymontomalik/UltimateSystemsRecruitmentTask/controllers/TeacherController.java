package pl.szymontomalik.UltimateSystemsRecruitmentTask.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Student;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Teacher;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.StudentService;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.TeacherService;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.ViewService;

@Controller
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ViewService service;

    @RequestMapping("/teachers")
    public String viewTeacherList(Model model) {
        return viewPageOfTeacherList(model, 1, "id", "asc");
    }

    @GetMapping("/teachers/page/{pageNum}")
    public String viewPageOfTeacherList(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @Param("sortField") String sortField,
                                        @Param("sortDir") String sortDir) {
        Page<Teacher> page = teacherService.listAll(pageNum, sortField, sortDir);
        service.setViewAttributes(model, pageNum, sortField, sortDir, page);
        return "teachers";
    }

    @RequestMapping("/student/{id}/teachers")
    public String viewAllStudentsTeacher(Model model, @PathVariable(name = "id") Long studentId) {
        return viewPageOfStudentsTeacher(model, studentId, 1, "id", "asc");
    }

    @RequestMapping("/student/{id}/teachers/page/{pageNum}")
    public String viewPageOfStudentsTeacher(Model model, @PathVariable(name = "id") Long studentId, @PathVariable(name = "pageNum") int pageNum,
                                            @Param("sortField") String sortField,
                                            @Param("sortDir") String sortDir) {
        Page<Teacher> page = teacherService.listAllTeacherByStudentId(studentId, pageNum, sortField, sortDir);
        service.setViewAttributes(model, pageNum, sortField, sortDir, page);
        Student student = studentService.get(studentId);
        model.addAttribute("studentName", student.getFirstname() + " " + student.getLastname());
        return "teachersByStudentId";
    }


}
