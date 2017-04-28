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
import ru.eninja.piaps.dao.SpecialtyDao;
import ru.eninja.piaps.domain.impl.Specialty;
import ru.eninja.piaps.util.specifications.Filter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/specialties")
public class SpecialtyController {

    public static final Map<String, String> FIELD_TO_NAME_MAP = new LinkedHashMap<String, String>() {{
        put("id", "№ специальности");
        put("name", "Название специальности");
        put("facultyByFacultyId.name", "Факультет");
    }};


    private final SpecialtyDao specialtyDao;
    private final FacultyDao facultyDao;

    @Autowired
    public SpecialtyController(SpecialtyDao specialtyDao, FacultyDao facultyDao) {
        this.specialtyDao = specialtyDao;
        this.facultyDao = facultyDao;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, params = {"filterFields", "filterWord"})
    public String showSpecialtyTable(Model model,
                                     @SortDefault("id") Pageable pageable,
                                     @RequestParam List<String> filterFields,
                                     @RequestParam String filterWord) {
        if (filterWord.isEmpty()) {
            return showSpecialtyTable(model, pageable);
        }

        Page page = specialtyDao.findAll(new Filter(filterFields, filterWord), pageable);
        model.addAttribute("page", page);
        model.addAttribute("filterFields", filterFields);
        model.addAttribute("filterWord", filterWord);

        return "specialties/specialtiesTable";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showSpecialtyTable(Model model, @SortDefault("id") Pageable pageable) {
        Page<Specialty> page = specialtyDao.findAll(pageable);
        model.addAttribute("page", page);
        return "specialties/specialtiesTable";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showSpecialty(Model model, @PathVariable("id") String id) {
        model.addAttribute("specialty", specialtyDao.findOne(id));
        return "specialties/specialtyDetails";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String initUpdateSpecialtyForm(Model model, @PathVariable("id") String id) {
        model.addAttribute("specialty", specialtyDao.findOne(id));
        model.addAttribute("faculties", facultyDao.findAll());
        return "specialties/createOrUpdateSpecialtyForm";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT)
    public String processUpdateSpecialtyForm(Specialty specialty, @PathVariable("id") String id) {
        specialty.setId(id);
        specialtyDao.save(specialty);
        return "redirect:/specialties";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public String processDeleteSpecialtyForm(@PathVariable("id") String id) {
        specialtyDao.delete(id);
        return "redirect:/specialties";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreateSpecialtyForm(Model model) {
        model.addAttribute("specialty", new Specialty());
        model.addAttribute("faculties", facultyDao.findAll());
        return "specialties/createOrUpdateSpecialtyForm";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreateSpecialtyForm(Specialty specialty) {
        specialtyDao.save(specialty);
        return "redirect:/specialties";
    }
}
