package com.dshard.freespace.service.impl;

import com.dshard.freespace.auth.AuthenticationService;
import com.dshard.freespace.model.Comment;
import com.dshard.freespace.persistance.CommentRepository;
import com.dshard.freespace.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<Comment> getCommentByBlogId(String blogId) {
        return commentRepository.findAllByBlogIdOrderByPostedDesc(blogId);
    }

    @Override
    public Comment saveComment(String blogId, String commentBody) {
        String authorName = authenticationService.getPrincipalName();
        Comment comment = Comment.builder()
                .body(commentBody)
                .blogId(blogId)
                .posted(LocalDateTime.now())
                .author(authorName)
                .build();
        return commentRepository.insert(comment);
    }
}
