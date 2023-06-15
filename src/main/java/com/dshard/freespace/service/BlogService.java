package com.dshard.freespace.service;

import com.dshard.freespace.model.Blog;

public interface BlogService {
    Blog saveBlog(Blog blog, String principalName);

    void deleteBlog(String id, String principalName);

    boolean isUserHasBlog(String blogId, String principalName);
}
