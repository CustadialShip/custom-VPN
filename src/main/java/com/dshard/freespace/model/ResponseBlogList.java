package com.dshard.freespace.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseBlogList {
    private List<Blog> blogs;
    private int currentPage;
    private long totalItems;
    private int totalPage;
}
