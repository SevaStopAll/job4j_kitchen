package ru.job4j.kitchen.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.kitchen.domain.Order;
import ru.job4j.kitchen.domain.Status;
import ru.job4j.kitchen.service.SimpleKitchenService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/kitchen")
public class OrderController {
    private final SimpleKitchenService kitchen;

    @GetMapping("/")
    public List<Order> findAll() {
        return (List<Order>) this.kitchen.findAll();
    }

    @Transactional
    @PatchMapping("/patch/{id}")
    public ResponseEntity<Order> patch(@RequestBody Status status, @PathVariable int id) {
        System.out.println(status.getId());
        System.out.println(status.getName());
        var order = kitchen.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        order.setStatus(kitchen.findStatusById(status.getId()).get());
        var rsl = kitchen.update(order);
        return new ResponseEntity<>(order, rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
