package com.dshard.freespace.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("blogs")
@Builder
public class Blog {
    @Id
    private String id;
    private String title;
    private String body;
    private String author;
    private String access;
}
