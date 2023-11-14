package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,Long> {
    List<Card> findAllByOrderByCreateAtDesc();
}