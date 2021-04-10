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
public class TeacherController {
    private final ViewService service;

    @RequestMapping("/teachers")
    public String viewTeacherList(Model model) {
        return viewPageOfTeacherList(model, 1, "id", "asc", null, true);
    }


    @GetMapping("/teachers/page/{pageNum}")
    public String viewPageOfTeacherList(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @Param("sortField") String sortField,
                                        @Param("sortDir") String sortDir,
                                        @Param("keyword") String keyword,
                                        @Param("flag") boolean flag) {
        return service.selectUserListForTeacherPage(model, pageNum, sortField, sortDir, keyword, flag);
    }
}
