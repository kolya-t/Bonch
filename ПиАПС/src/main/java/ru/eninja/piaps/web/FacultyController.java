package ru.eninja.piaps.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.eninja.piaps.dao.FacultyDao;
import ru.eninja.piaps.domain.Faculty;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyDao facultyDao;

    @Autowired
    public FacultyController(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllStudents(Model model, Pageable pageable) {
        Page<Faculty> page = facultyDao.findAll(pageable);
        model.addAttribute("faculties", page);
        return "faculties/list";
    }
}
