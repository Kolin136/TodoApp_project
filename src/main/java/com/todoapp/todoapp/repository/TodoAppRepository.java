package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoAppRepository extends JpaRepository<Card,Long> {
}