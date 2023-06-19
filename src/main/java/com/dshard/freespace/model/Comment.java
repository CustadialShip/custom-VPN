package com.dshard.freespace.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("comments")
@Builder
public class Comment {
    @Id
    private String id;
    private String body;
    private String author;
    private String blogId;
    private LocalDateTime posted;
}
