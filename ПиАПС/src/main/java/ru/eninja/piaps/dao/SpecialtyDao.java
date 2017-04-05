package ru.eninja.piaps.dao;

import org.springframework.data.repository.CrudRepository;
import ru.eninja.piaps.domain.Specialty;


public interface SpecialtyDao extends CrudRepository<Specialty, String> {
}
