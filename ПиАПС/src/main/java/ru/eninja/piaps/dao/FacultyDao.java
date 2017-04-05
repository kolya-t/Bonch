package ru.eninja.piaps.dao;

import org.springframework.data.repository.CrudRepository;
import ru.eninja.piaps.domain.Faculty;


public interface FacultyDao extends CrudRepository<Faculty, Integer> {
}
