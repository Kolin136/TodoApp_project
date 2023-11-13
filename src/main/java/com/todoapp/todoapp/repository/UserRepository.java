package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
