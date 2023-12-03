package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments,Long> {

}
