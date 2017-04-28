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
import ru.eninja.piaps.dao.FacultyDao;
import ru.eninja.piaps.dao.GroupDao;
import ru.eninja.piaps.dao.SpecialtyDao;
import ru.eninja.piaps.dao.StudentDao;
import ru.eninja.piaps.domain.impl.Faculty;
import ru.eninja.piaps.domain.impl.Specialty;
import ru.eninja.piaps.domain.impl.Student;
import ru.eninja.piaps.util.specifications.Filter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/students")
public class StudentController {

    public static final Map<String, String> FIELD_TO_NAME_MAP = new LinkedHashMap<String, String>() {{
        put("id", "№ зачётки");
        put("lastName", "Фамилия");
        put("firstName", "Имя");
        put("birthDate", "Дата рождения");
        put("groupByGroupId.id", "Группа");
        put("groupByGroupId.course", "Курс");
        put("groupByGroupId.specialtyBySpecialtyId.id", "№ специальности");
        put("groupByGroupId.specialtyBySpecialtyId.facultyByFacultyId.name", "Факультет");
    }};


    private final StudentDao studentDao;
    private final FacultyDao facultyDao;
    private final SpecialtyDao specialtyDao;
    private final GroupDao groupDao;

    @Autowired
    public StudentController(StudentDao studentDao, FacultyDao facultyDao, SpecialtyDao specialtyDao, GroupDao groupDao) {
        this.studentDao = studentDao;
        this.facultyDao = facultyDao;
        this.specialtyDao = specialtyDao;
        this.groupDao = groupDao;
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

        return "students/studentsTable";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllStudents(Model model, @SortDefault("id") Pageable pageable) {
        Page<Student> page = studentDao.findAll(pageable);
        model.addAttribute("page", page);
        return "students/studentsTable";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showStudent(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("student", studentDao.findOne(id));
        return "students/studentDetails";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String initUpdateStudentForm(Model model, @PathVariable("id") Integer id) {
        Student student = studentDao.findOne(id);
        Specialty currentSpecialty = student.getGroupByGroupId().getSpecialtyBySpecialtyId();
        Faculty currentFaculty = currentSpecialty.getFacultyByFacultyId();

        model.addAttribute("student", student);
        model.addAttribute("faculties", facultyDao.findAll());
        model.addAttribute("specialties", currentFaculty.getSpecialtiesById());
        model.addAttribute("groups", currentSpecialty.getGroupsById());
        return "students/createOrUpdateStudentForm";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT)
    public String processUpdateStudentForm(Student student, @PathVariable("id") Integer id) {
        student.setId(id);
        studentDao.save(student);
        return "redirect:/students";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public String processDeleteStudentForm(@PathVariable("id") Integer id) {
        studentDao.delete(id);
        return "redirect:/students";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreateStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("faculties", facultyDao.findAll());
        return "students/createOrUpdateStudentForm";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreateStudentForm(Student student) {
        studentDao.save(student);
        return "redirect:/students";
    }
}