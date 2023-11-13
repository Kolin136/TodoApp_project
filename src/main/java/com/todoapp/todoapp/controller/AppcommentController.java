package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.service.AppCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/todo")
@RestController
public class AppcommentController {
    private final AppCommentService appCommentService;

    public AppcommentController(AppCommentService appCommentService) {
        this.appCommentService = appCommentService;
    }
}
