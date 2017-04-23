package ru.eninja.piaps.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.eninja.piaps.dao.GroupDao;
import ru.eninja.piaps.dao.SpecialtyDao;
import ru.eninja.piaps.domain.impl.Group;

@RestController
@RequestMapping("/rest/groups")
public class GroupRestController {

    private final GroupDao groupDao;
    private final SpecialtyDao specialtyDao;

    @Autowired
    public GroupRestController(GroupDao groupDao, SpecialtyDao specialtyDao) {
        this.groupDao = groupDao;
        this.specialtyDao = specialtyDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Group> getAllGroups() {
        return groupDao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, params = "specialtyId")
    public Iterable<Group> getGroupsBySpecialtyId(@RequestParam(value = "specialtyId") String specialtyId) {
        return specialtyDao.findOne(specialtyId).getGroupsById();
    }

    @RequestMapping(method = RequestMethod.GET, params = "groupId")
    public Group getGroupByGroupId(@RequestParam(value = "groupId") String groupId) {
        return groupDao.findOne(groupId);
    }
}
