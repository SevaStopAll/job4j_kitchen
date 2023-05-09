package ru.job4j.kitchen.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.kitchen.domain.Order;
import ru.job4j.kitchen.domain.Status;
import ru.job4j.kitchen.repository.OrderRepository;
import ru.job4j.kitchen.repository.StatusRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SimpleKitchenService {
    private final OrderRepository orders;
    private final StatusRepository statuses;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Создать новый заказ, полученный из сервиса заказов.
     *
     * @param order заказ.
     * @return Optional заказа.
     */
    @KafkaListener(topics = "job4j_preorder")
    public void receiveOrder(Map order) {
        Order kitchenOrder = new Order();
        String dishes = order.get("dishes").toString();
        kitchenOrder.setDescription(dishes);
        kitchenOrder.setStatus(new Status(Integer.parseInt(order.get("status").toString()), "Создан"));
        log.debug(dishes);
        log.debug(order.get("time").toString());
        orders.save(kitchenOrder);
    }

    /**
     * Изменить заказ.
     *
     * @param order заказ.
     * @return результат изменения.
     */
    @Transactional
    public boolean update(Order order) {
        if (orders.findById(order.getId()).isEmpty()) {
            return false;
        }
        orders.save(order);
        changeStatus(order);
        return true;
    }

    /**
     * Получить список заказов.
     *
     * @return Список заказов кухни.
     */
    public Collection<Order> findAll() {
        return orders.findAll();
    }

    /**
     * Найти заказ по идентификатору.
     *
     * @param id идентификатор заказа.
     * @return Optional заказа.
     */

    public Optional<Order> findById(int id) {
        return orders.findById(id);
    }

    /**
     * Найти статус заказа по идентификатору статуса.
     *
     * @param id идентификатор статуса.
     * @return Optional статуса заказа.
     */

    public Optional<Status> findStatusById(int id) {
        return statuses.findById(id);
    }

    /**
     * Отправить изменения о статусе заказа в главный сервис.
     *
     * @param order заказ с идентификатором.
     */
    @Transactional
    public void changeStatus(Order order) {
        Map<String, Integer> data = new HashMap();
        data.put("id", order.getId());
        data.put("status", order.getStatus().getId());
        kafkaTemplate.send("cooked_order", data);
    }

}