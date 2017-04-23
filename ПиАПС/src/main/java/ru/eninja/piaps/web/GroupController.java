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
import ru.eninja.piaps.dao.GroupDao;
import ru.eninja.piaps.domain.impl.Group;
import ru.eninja.piaps.util.specifications.Filter;

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

    @Autowired
    public GroupController(GroupDao groupDao) {
        this.groupDao = groupDao;
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
}
