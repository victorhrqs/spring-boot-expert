package br.com.udemy.service;

import br.com.udemy.dto.*;
import br.com.udemy.entities.Client;
import br.com.udemy.entities.Order;
import br.com.udemy.entities.OrderItem;
import br.com.udemy.entities.Product;
import br.com.udemy.enums.StatusOrder;
import br.com.udemy.exception.NotFoundException;
import br.com.udemy.repositories.OrderItemRepository;
import br.com.udemy.repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Transactional // or salve all or all rollback
    public OrderDTO store (OrderDTO orderDTO) {

        // verify if client exists
        ClientDTO clientFound = clientService.findById(orderDTO.getClient());

        Client client = new Client();
        Order order = new Order();

        // populate client
        client.setId(orderDTO.getClient());
        client.setName(clientFound.getName());
        client.setEmail(clientFound.getEmail());

        // populate order
        order.setTotal(orderDTO.getTotal());
        order.setOrderDate(LocalDate.now());
        order.setClient(client);
        order.setStatus(StatusOrder.accomplished);

        List<OrderItem> orderItems = converterOrderItems(order, orderDTO.getItems());

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        order.setOrderItemList(orderItems);

        return OrderDTO.builder()
                .client(order.getClient().getId())
                .orderDate(order.getOrderDate())
                .total(order.getTotal())
                .items(order.getOrderItemList().stream().map( o -> {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();

                    orderItemDTO.setProduct(o.getProduct().getId());
                    orderItemDTO.setQuantity(o.getQuantity());

                    return orderItemDTO;
                }).collect(Collectors.toList()))
                .build();
    }
//
    private List<OrderItem> converterOrderItems (Order order, List<OrderItemDTO> orderItemDTOList ) {
        if ( orderItemDTOList.isEmpty() ) throw new NotFoundException("Order item list is empty");

        return orderItemDTOList.stream()
                    .map( item -> {
                        // find product by id
                        Product product = productService.findById(item.getProduct());

                        // populate orderitem
                        OrderItem orderItem = new OrderItem();

                        orderItem.setQuantity(item.getQuantity());
                        orderItem.setOrder(order);
                        orderItem.setProduct(product);

                        return orderItem;
                    }).collect(Collectors.toList());
    }

    public Order getFullOrder ( Integer id ) {
        return orderRepository.findByIdFetchOrderItemList(id).orElseThrow(() -> new NotFoundException("Order not found: " + id));
    }

    @Transactional
    public void updateStatusOrder (Integer id, StatusOrder status) {
        orderRepository
                .findById(id)
                .map( order -> {
                    order.setStatus(status);

                    return orderRepository.save(order);
                })
                .orElseThrow( () -> new NotFoundException("Order not found"));
    }
}
