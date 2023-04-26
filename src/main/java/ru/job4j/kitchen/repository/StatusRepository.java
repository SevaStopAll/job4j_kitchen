package ru.job4j.kitchen.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.kitchen.domain.Status;

public interface StatusRepository extends CrudRepository<Status, Integer> {
}
