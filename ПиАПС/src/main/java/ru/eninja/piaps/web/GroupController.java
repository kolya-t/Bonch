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
import ru.eninja.piaps.domain.impl.Faculty;
import ru.eninja.piaps.domain.impl.Group;
import ru.eninja.piaps.domain.impl.Specialty;
import ru.eninja.piaps.util.specifications.Filter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/groups")
public class GroupController {
    public static final Map<String, String> FIELD_TO_NAME_MAP = new LinkedHashMap<String, String>() {{
        put("id", "№ группы");
        put("course", "Курс");
        put("specialtyBySpecialtyId.id", "№ специальности");
        put("specialtyBySpecialtyId.name", "Название специальности");
        put("specialtyBySpecialtyId.facultyByFacultyId.name", "Факультет");
    }};


    private final GroupDao groupDao;
    private final FacultyDao facultyDao;

    @Autowired
    public GroupController(GroupDao groupDao, FacultyDao facultyDao) {
        this.groupDao = groupDao;
        this.facultyDao = facultyDao;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, params = {"filterFields", "filterWord"})
    public String showGroupsTable(Model model,
                                     @SortDefault("id") Pageable pageable,
                                     @RequestParam List<String> filterFields,
                                     @RequestParam String filterWord) {
        if (filterWord.isEmpty()) {
            return showGroupsTable(model, pageable);
        }

        Page page = groupDao.findAll(new Filter(filterFields, filterWord), pageable);
        model.addAttribute("page", page);
        model.addAttribute("filterFields", filterFields);
        model.addAttribute("filterWord", filterWord);

        return "groups/groupsTable";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showGroupsTable(Model model, @SortDefault("id") Pageable pageable) {
        Page<Group> page = groupDao.findAll(pageable);
        model.addAttribute("page", page);
        return "groups/groupsTable";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showGroup(Model model, @PathVariable("id") String id) {
        model.addAttribute("group", groupDao.findOne(id));
        return "groups/groupDetails";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String initUpdateGroupForm(Model model, @PathVariable("id") String id) {
        Group group = groupDao.findOne(id);
        Specialty currentSpecialty = group.getSpecialtyBySpecialtyId();
        Faculty currentFaculty = currentSpecialty.getFacultyByFacultyId();

        model.addAttribute("group", group);
        model.addAttribute("faculties", facultyDao.findAll());
        model.addAttribute("specialties", currentFaculty.getSpecialtiesById());
        return "groups/createOrUpdateGroupForm";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT)
    public String processUpdateGroupForm(Group group, @PathVariable("id") String id) {
        group.setId(id);
        groupDao.save(group);
        return "redirect:/groups";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public String processDeleteGroupForm(@PathVariable("id") String id) {
        groupDao.delete(id);
        return "redirect:/groups";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreateGroupForm(Model model) {
        model.addAttribute("group", new Group());
        model.addAttribute("faculties", facultyDao.findAll());
        return "groups/createOrUpdateGroupForm";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreateGroupForm(Group group) {
        groupDao.save(group);
        return "redirect:/groups";
    }
}
