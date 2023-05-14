package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepostitory extends JpaRepository<Order, Integer> {
    List<Order> findByPerson (Person person);

    @Query(value = "select * from orders where onumber like %?1", nativeQuery = true)
    List<Order> findOrderByPartOfNumber(String onumber);

}