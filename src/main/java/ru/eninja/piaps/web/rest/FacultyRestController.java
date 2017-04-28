package ru.eninja.piaps.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.eninja.piaps.dao.FacultyDao;
import ru.eninja.piaps.domain.impl.Faculty;

@RestController
@RequestMapping("/rest/faculties")
public class FacultyRestController {

    private final FacultyDao facultyDao;

    @Autowired
    public FacultyRestController(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Faculty> getAllFaculties() {
        return facultyDao.findAll();
    }
}
