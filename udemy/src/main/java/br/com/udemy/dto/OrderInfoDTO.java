package br.com.udemy.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderInfoDTO {
    private Integer cod;
    private String name;
    private String email;
    private BigDecimal total;
    private LocalDate orderDate;
    private String status;
    private List<OrderItemInfoDTO> items;

}
