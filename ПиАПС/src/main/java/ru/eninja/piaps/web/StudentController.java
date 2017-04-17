package ru.eninja.piaps.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.eninja.piaps.dao.StudentDao;
import ru.eninja.piaps.domain.Student;
import ru.eninja.piaps.util.specifications.Filter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/students")
public class StudentController {

    public static final Map<String, String> FIELD_TO_NAME_MAP = new LinkedHashMap<String, String>() {{
        put("id", "Номер зачётки");
        put("lastName", "Фамилия");
        put("firstName", "Имя");
        put("birthDate", "Дата рождения");
        put("groupByGroupId.id", "Группа");
        put("groupByGroupId.course", "Курс");
        put("groupByGroupId.specialtyBySpecialtyId.id", "Специальность");
        put("groupByGroupId.specialtyBySpecialtyId.facultyByFacultyId.name", "Факультет");
    }};


    private final StudentDao studentDao;

    @Autowired
    public StudentController(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, params = {"filterFields", "filterWord"})
    public String getAllStudents(Model model,
                                 @SortDefault("lastName") Pageable pageable,
                                 @RequestParam List<String> filterFields,
                                 @RequestParam String filterWord) {
        if (filterWord.isEmpty()) {
            return getAllStudents(model, pageable);
        }

        Page page = studentDao.findAll(new Filter(filterFields, filterWord), pageable);
        model.addAttribute("page", page);
        model.addAttribute("filterFields", filterFields);
        model.addAttribute("filterWord", filterWord);

        return "students/table";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllStudents(Model model, @SortDefault("lastName") Pageable pageable) {
        Page<Student> page = studentDao.findAll(pageable);
        model.addAttribute("page", page);
        return "students/table";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getStudent(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("student", studentDao.findOne(id));
        return "students/student";
    }
}