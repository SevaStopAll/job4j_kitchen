package ru.job4j.kitchen.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.kitchen.domain.Order;

import java.util.Collection;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    Collection<Order> findAll();
}
