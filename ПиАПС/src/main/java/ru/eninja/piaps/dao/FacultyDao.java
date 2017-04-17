package ru.eninja.piaps.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.eninja.piaps.domain.Faculty;


public interface FacultyDao extends PagingAndSortingRepository<Faculty, Integer>, JpaSpecificationExecutor<Faculty> {
}
