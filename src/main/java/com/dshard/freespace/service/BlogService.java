package com.dshard.freespace.service;

import com.dshard.freespace.model.Blog;
import com.dshard.freespace.model.RequestFormBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Blog saveBlog(RequestFormBlog requestFormBlog, String principalName);

    void deleteBlog(String id, String principalName);

    boolean isUserHasBlog(String blogId, String principalName);

    Page<Blog> getBlogsByUserAccessAndPagination(String principalName, Pageable pageable);
}
