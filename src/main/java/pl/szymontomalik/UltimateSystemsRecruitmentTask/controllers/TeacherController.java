package pl.szymontomalik.UltimateSystemsRecruitmentTask.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.entities.Teacher;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.TeacherService;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.ViewService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final ViewService service;
    private final TeacherService teacherService;

    @RequestMapping("")
    public String viewTeacherList(Model model) {
        return viewPageOfTeacherList(model, 1, "id", "asc", null, true);
    }


    @RequestMapping("/page/{pageNum}")
    public String viewPageOfTeacherList(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @Param("sortField") String sortField,
                                        @Param("sortDir") String sortDir,
                                        @Param("keyword") String keyword,
                                        @Param("flag") boolean flag) {
        return service.selectUserListForTeacherPage(model, pageNum, sortField, sortDir, keyword, flag);
    }

    @GetMapping("/add")
    public String addNewTeacherForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "teacherForm";
    }

    @PostMapping("/add")
    public String saveNewTeacher(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teacherForm";
        }
        teacherService.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public String editTeacher(Model model, @PathVariable("id") Long teacherId) {
        Teacher teacher = teacherService.get(teacherId);
        model.addAttribute("teacher", teacher);
        return "editTeacherForm";
    }

    @PostMapping("/edit/{id}")
    public String saveEditedTeacher(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editTeacherForm";
        }
        teacherService.save(teacher);
        return "redirect:/teachers";
    }

    @RequestMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable("id") Long teacherId) {
        teacherService.delete(teacherId);
        return "redirect:/teachers";
    }

}
