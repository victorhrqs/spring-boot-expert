package br.com.udemy.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;

    @OneToMany( mappedBy = "client" ) // mapeado na outra entidade por "client"
    private List<Order> orders;
}
