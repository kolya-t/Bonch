package ru.eninja.piaps.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.eninja.piaps.domain.Student;


public interface StudentDao extends PagingAndSortingRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
}
