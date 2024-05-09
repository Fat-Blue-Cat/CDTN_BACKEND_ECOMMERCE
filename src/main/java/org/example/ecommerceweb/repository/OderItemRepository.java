package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OderItemRepository extends JpaRepository<OrderItem, Long> {
}
