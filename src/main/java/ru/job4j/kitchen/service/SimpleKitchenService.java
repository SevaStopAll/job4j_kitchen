package ru.job4j.kitchen.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SimpleKitchenService  {

    @KafkaListener(topics = "job4j_orders")
    public void receiveOrder(Map order) {
        log.debug(order.get("dishes").toString());
    }
}