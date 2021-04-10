package pl.szymontomalik.UltimateSystemsRecruitmentTask.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.services.ViewService;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final ViewService service;

    @RequestMapping("/students")
    public String viewStudentList(Model model) {
        return viewPageOfStudentList(model, 1, "id", "asc", null, true);
    }

    @GetMapping("/students/page/{pageNum}")
    public String viewPageOfStudentList(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @Param("sortField") String sortField,
                                        @Param("sortDir") String sortDir,
                                        @Param("keyword") String keyword,
                                        @Param("flag") boolean flag) {
        return service.selectUserListForStudentPage(model, pageNum, sortField, sortDir, keyword, flag);
    }
}
