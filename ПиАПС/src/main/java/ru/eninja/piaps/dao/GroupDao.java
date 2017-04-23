package ru.eninja.piaps.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.eninja.piaps.domain.impl.Group;


public interface GroupDao extends PagingAndSortingRepository<Group, String>, JpaSpecificationExecutor<Group> {
}
