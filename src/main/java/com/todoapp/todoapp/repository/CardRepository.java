package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,Long> {
    List<Card> findAllByOrderByCreateAtDesc();
    List<Card> findAllByUser(User user);

    List<Card> findAllByOrderByUserUsernameAscCreateAtDesc();

}