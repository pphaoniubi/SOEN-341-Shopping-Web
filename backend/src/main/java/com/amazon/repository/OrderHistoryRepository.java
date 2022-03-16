package com.amazon.repository;

import com.amazon.entity.Account;
import com.amazon.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {

    OrderHistory findByAccountAndId(Account account, int orderHistoryId);
}
