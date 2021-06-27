package org.orm.remiders;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindersPaginationRepository extends PagingAndSortingRepository<Reminders, Integer> {
}