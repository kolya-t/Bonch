package ru.eninja.piaps.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.eninja.piaps.domain.Group;


public interface GroupDao extends PagingAndSortingRepository<Group, String> {
}
