package pl.szymontomalik.UltimateSystemsRecruitmentTask.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Student;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.StudentService;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.TeacherService;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.ViewService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final ViewService service;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @RequestMapping("")
    public String viewStudentList(Model model) {
        return viewPageOfStudentList(model, 1, "id", "asc", null, true);
    }

    @RequestMapping("/page/{pageNum}")
    public String viewPageOfStudentList(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @Param("sortField") String sortField,
                                        @Param("sortDir") String sortDir,
                                        @Param("keyword") String keyword,
                                        @Param("flag") boolean flag) {
        return service.selectUserListForStudentPage(model, pageNum, sortField, sortDir, keyword, flag);
    }

    @GetMapping("/add")
    public String addNewStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("newStudent", student);
        return "studentForm";
    }

    @PostMapping("/add")
    public String saveNewStudent(@Valid @ModelAttribute("newStudent") Student student, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            studentService.save(student);
            return "redirect:/students";
        }
        return "studentForm";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(Model model, @PathVariable("id") Long studentId) {
        Student student = studentService.get(studentId);
        model.addAttribute("student", student);
        return "editStudentForm";
    }

    @PostMapping("/edit/{id}")
    public String saveEditedStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editStudentForm";
        }
        studentService.save(student);
        return "redirect:/students";
    }

    @RequestMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long studentId) {
        studentService.delete(studentId);
        return "redirect:/students";
    }

    @RequestMapping("/details/{id}")
    public String showStudentDetails(Model model, @PathVariable("id") Long studentId) {
        return pageStudentDetails(model, studentId, 1, "id", "asc");
    }

    @RequestMapping("/details/{id}/page/{pageNum}")
    public String pageStudentDetails(Model model, @PathVariable("id") Long studentId, @PathVariable(name = "pageNum") int pageNum,
                                     @Param("sortField") String sortField,
                                     @Param("sortDir") String sortDir) {
        model.addAttribute("userDetails",studentService.get(studentId));
        service.pageTeachersByStudentId(model, pageNum, sortField, sortDir, studentId);
        model.addAttribute("availableTeachers", studentService.findAvailableTeachers(studentId));
        return "studentDetails";
    }

    @RequestMapping("/details/{studentId}/delete/teacher/{teacherId}")
    public String deleteTeacherFromStudentList(@PathVariable("studentId") Long studentId, @PathVariable("teacherId") Long teacherId) {
        studentService.deleteTeacherFromList(studentId, teacherId);
        teacherService.deleteStudentFromList(teacherId, studentId);
        return "redirect:/students/details/" + studentId;
    }

    @PostMapping("/details/{id}/add/teacher")
    public String addTeacher(@PathVariable("id") Long studentId, @RequestParam("newTeacher") Long teacherId) {
        studentService.addTeacherToMyList(studentId, teacherId);
        teacherService.addStudentToList(teacherId, studentId);
        return "redirect:/students/details/" + studentId;

    }

}
