package com.dshard.freespace.service;

import com.dshard.freespace.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentByBlogId(String blogId);

    Comment saveComment(String blogId, String commentBody);
}
