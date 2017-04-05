package ru.eninja.piaps.dao;

import org.springframework.data.repository.CrudRepository;
import ru.eninja.piaps.domain.Student;


public interface StudentDao extends CrudRepository<Student, Integer> {
}
