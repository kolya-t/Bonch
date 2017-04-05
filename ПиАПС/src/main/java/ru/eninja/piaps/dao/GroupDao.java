package ru.eninja.piaps.dao;

import org.springframework.data.repository.CrudRepository;
import ru.eninja.piaps.domain.Group;


public interface GroupDao extends CrudRepository<Group, String> {
}
