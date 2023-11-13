package com.todoapp.todoapp.service;

import com.todoapp.todoapp.repository.TodoAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AppCommentService {
    private final TodoAppRepository todoAppRepository;
}
