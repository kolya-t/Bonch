package ru.eninja.piaps.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.eninja.piaps.domain.Specialty;


public interface SpecialtyDao extends PagingAndSortingRepository<Specialty, String> {
}
