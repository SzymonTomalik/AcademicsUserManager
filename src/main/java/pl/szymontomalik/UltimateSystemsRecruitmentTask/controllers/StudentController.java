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
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.ViewService;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.StudentService;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.TeacherService;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ViewService service;

    @RequestMapping("/students")
    public String viewStudentList(Model model) {
        return viewPageOfStudentList(model, 1, "id", "asc");
    }

    @GetMapping("/students/page/{pageNum}")
    public String viewPageOfStudentList(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @Param("sortField") String sortField,
                                        @Param("sortDir") String sortDir) {
        Page<Student> page = studentService.listAll(pageNum, sortField, sortDir);
        service.setViewAttributes(model, pageNum, sortField, sortDir, page);
        return "students";
    }


    @RequestMapping("/teacher/{id}/students")
    public String viewAllTeachersStudent(Model model, @PathVariable(name = "id") Long teacherId) {

        return viewPageOfTeachersStudent(model, teacherId, 1, "id", "asc");
    }

    @RequestMapping("/teacher/{id}/students/page/{pageNum}")
    public String viewPageOfTeachersStudent(Model model, @PathVariable(name = "id") Long teacherId, @PathVariable(name = "pageNum") int pageNum,
                                            @Param("sortField") String sortField,
                                            @Param("sortDir") String sortDir) {
        Page<Student> page = studentService.listAllStudentByTeacherId(teacherId, pageNum, sortField, sortDir);
        service.setViewAttributes(model, pageNum, sortField, sortDir, page);
        Teacher teacher = teacherService.get(teacherId);
        model.addAttribute("teacherName", teacher.getFirstname() + " " + teacher.getLastname());
        return "studentsByTeacherId";
    }



}
