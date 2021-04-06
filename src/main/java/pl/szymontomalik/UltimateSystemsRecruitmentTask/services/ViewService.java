package pl.szymontomalik.UltimateSystemsRecruitmentTask.services;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import pl.szymontomalik.UltimateSystemsRecruitmentTask.model.AcademicUser;

import java.util.List;

@Service
public class ViewService {
    public void setViewAttributes(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField, @Param("sortDir") String sortDir, Page<? extends AcademicUser> page) {
        List<? extends AcademicUser> userList = page.getContent();
        model.addAttribute("userList", userList);
        setSorting(model, pageNum, sortField, sortDir, page.getTotalPages(), page.getTotalElements());
    }
    public void setSorting(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField, @Param("sortDir") String sortDir, int totalPages, long totalElements) {
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalElements);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }
}
