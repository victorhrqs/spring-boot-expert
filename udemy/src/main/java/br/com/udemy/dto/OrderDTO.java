package br.com.udemy.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderDTO {
    private Integer client;
    private LocalDate orderDate;
    private BigDecimal total;

    private List<OrderItemDTO> items;
}
