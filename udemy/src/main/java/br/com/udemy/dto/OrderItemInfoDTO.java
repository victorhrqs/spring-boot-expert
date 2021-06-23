package br.com.udemy.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemInfoDTO {
    private String product_description;
    private Float price;
    private Integer quantity;
}
