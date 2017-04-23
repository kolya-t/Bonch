package ru.eninja.piaps.web.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.eninja.piaps.dao.StudentDao;
import ru.eninja.piaps.domain.impl.Student;


@RestController
@RequestMapping("/rest/students")
public class StudentRestController {

    private final StudentDao studentDao;

    @Autowired
    public StudentRestController(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Student> getAllStudents() {
        return studentDao.findAll();
    }
}
