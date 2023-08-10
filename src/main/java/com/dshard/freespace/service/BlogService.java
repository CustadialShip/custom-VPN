package com.dshard.freespace.service;

import com.dshard.freespace.model.Blog;
import com.dshard.freespace.model.RequestFormBlog;

public interface BlogService {
    Blog saveBlog(RequestFormBlog requestFormBlog, String principalName);

    void deleteBlog(String id, String principalName);

    boolean isUserHasBlog(String blogId, String principalName);
}
