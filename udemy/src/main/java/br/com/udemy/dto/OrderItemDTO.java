package br.com.udemy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    @NotNull(message = "product field is required")
    private Integer product;

    @NotNull(message = "quantity field is required")
    private Integer quantity;
}
