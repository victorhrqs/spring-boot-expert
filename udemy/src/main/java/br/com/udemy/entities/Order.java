package br.com.udemy.entities;

import br.com.udemy.enums.StatusOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "tb_order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private LocalDate orderDate;

    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StatusOrder status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList;

}
