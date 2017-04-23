package ru.eninja.piaps.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.eninja.piaps.dao.FacultyDao;
import ru.eninja.piaps.dao.SpecialtyDao;
import ru.eninja.piaps.domain.impl.Specialty;


@RestController
@RequestMapping("/rest/specialties")
public class SpecialtyRestController {

    private final SpecialtyDao specialtyDao;
    private final FacultyDao facultyDao;

    @Autowired
    public SpecialtyRestController(SpecialtyDao specialtyDao, FacultyDao facultyDao) {
        this.specialtyDao = specialtyDao;
        this.facultyDao = facultyDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Specialty> getAllSpecialties() {
        return specialtyDao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, params = "facultyId")
    public Iterable<Specialty> getSpecialtiesByFacultyId(@RequestParam(value = "facultyId") Integer facultyId) {
        return facultyDao.findOne(facultyId).getSpecialtiesById();
    }
}
