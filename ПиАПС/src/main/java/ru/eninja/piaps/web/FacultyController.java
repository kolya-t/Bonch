package ru.eninja.piaps.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.eninja.piaps.dao.FacultyDao;
import ru.eninja.piaps.domain.Faculty;
import ru.eninja.piaps.util.specifications.Filter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/faculties")
public class FacultyController {

    public static final Map<String, String> FIELD_TO_NAME_MAP = new LinkedHashMap<String, String>() {{
        put("id", "ID");
        put("name", "Название");
    }};

    private final FacultyDao facultyDao;

    @Autowired
    public FacultyController(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, params = {"filterFields", "filterWord"})
    public String showFacultiesTable(Model model,
                                 @SortDefault("id") Pageable pageable,
                                 @RequestParam List<String> filterFields,
                                 @RequestParam String filterWord) {
        if (filterWord.isEmpty()) {
            return showFacultiesTable(model, pageable);
        }

        Page page = facultyDao.findAll(new Filter(filterFields, filterWord), pageable);
        model.addAttribute("page", page);
        model.addAttribute("filterFields", filterFields);
        model.addAttribute("filterWord", filterWord);

        return "faculties/table";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showFacultiesTable(Model model, @SortDefault("id") Pageable pageable) {
        Page<Faculty> page = facultyDao.findAll(pageable);
        model.addAttribute("page", page);
        return "faculties/table";
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String showFacultiesTree(Model model, Pageable pageable) {
        Page<Faculty> page = facultyDao.findAll(pageable);
        model.addAttribute("page", page);
        return "faculties/tree";
    }
}
