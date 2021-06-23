package br.com.udemy.controller;

import br.com.udemy.dto.OrderDTO;
import br.com.udemy.dto.OrderInfoDTO;
import br.com.udemy.dto.OrderItemInfoDTO;
import br.com.udemy.dto.UpdateOrderStatusDTO;
import br.com.udemy.entities.Order;
import br.com.udemy.entities.OrderItem;
import br.com.udemy.enums.StatusOrder;
import br.com.udemy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> store (@RequestBody OrderDTO orderDTO) {
        return new ResponseEntity(orderService.store(orderDTO), CREATED);
    }

    @GetMapping("/{id}")
    public OrderInfoDTO findFullOrderById (@PathVariable Integer id) {
        Order order = orderService.getFullOrder(id);

        return OrderInfoDTO.builder()
                .cod(order.getId())
                .name(order.getClient().getName())
                .email(order.getClient().getEmail())
                .total(order.getTotal())
                .orderDate(LocalDate.parse(order.getOrderDate().toString()))
                .status(!(order.getStatus().name().isEmpty()) ? order.getStatus().name() : null)
                .items(converterOrderItemList(order.getOrderItemList()))
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus (@PathVariable Integer id, @RequestBody UpdateOrderStatusDTO status) {
        String statusStr = status.getStatus();
        orderService.updateStatusOrder(id, StatusOrder.valueOf(statusStr));
    }

    private List<OrderItemInfoDTO> converterOrderItemList (List<OrderItem> items) {
        if ( items.isEmpty() ) return Collections.emptyList();

        return items.stream()
                    .map( item ->
                            OrderItemInfoDTO
                                    .builder()
                                    .product_description(item.getProduct().getName())
                                    .price(item.getProduct().getPrice())
                                    .quantity(item.getQuantity())
                                    .build())
                .collect(Collectors.toList());
    }
}
