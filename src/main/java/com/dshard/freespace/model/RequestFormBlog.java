package com.dshard.freespace.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestFormBlog {
    private String title;
    private String body;
    private String access;
}
