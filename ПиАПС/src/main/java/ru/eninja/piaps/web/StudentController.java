package ru.eninja.piaps.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.eninja.piaps.dao.StudentDao;


@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllStudents(Model model, @SortDefault("lastName") Pageable pageable) {
        model.addAttribute("page", studentDao.findAll(pageable));
        return "students/list";
    }
}
