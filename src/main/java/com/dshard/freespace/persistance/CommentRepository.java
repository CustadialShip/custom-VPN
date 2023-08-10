package com.dshard.freespace.persistance;

import com.dshard.freespace.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBlogIdOrderByPostedDesc(String username);

}
