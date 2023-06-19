package com.dshard.freespace.controller;

import com.dshard.freespace.model.Comment;
import com.dshard.freespace.model.RequestFormComment;
import com.dshard.freespace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    private List<Comment> getCommentById(@PathVariable String id) {
        return commentService.getCommentByBlogId(id);
    }

    @PostMapping
    private String saveBlog(@RequestBody RequestFormComment form) {
        return commentService.saveComment(form.getBlogId(), form.getCommentMessage()).getId();
    }
}
