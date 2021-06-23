package br.com.udemy.repositories;

import br.com.udemy.dto.OrderDTO;
import br.com.udemy.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

//    @Query(" select o from Order o left join fetch o.orderItemList where o.id = :id ")
    @Query(value = "SELECT * FROM tb_order A LEFT JOIN tb_order_item B ON B.order_id = A.id WHERE A.id = :id", nativeQuery = true)
    Optional<Order> findByIdFetchOrderItemList(@Param("id") Integer id);

}
