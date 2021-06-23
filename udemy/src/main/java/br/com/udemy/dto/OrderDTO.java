package br.com.udemy.dto;

import br.com.udemy.validation.NotEmptyList;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderDTO {

    @NotNull(message = "client field is required")
    private Integer client;
    private LocalDate orderDate;

    @NotNull(message = "total field is required")
    private BigDecimal total;

    @NotEmptyList
    private List<OrderItemDTO> items;
}
