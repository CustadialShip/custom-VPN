package com.dshard.freespace.service.impl;

import com.dshard.freespace.model.Blog;
import com.dshard.freespace.model.User;
import com.dshard.freespace.persistance.BlogRepository;
import com.dshard.freespace.persistance.UserRepository;
import com.dshard.freespace.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Override
    public Blog saveBlog(Blog blog, String principalName) {
        User currentUser = userRepository.findByUsername(principalName).orElseThrow();
        blog.setAuthor(principalName);
        Blog dbBlog = blogRepository.save(blog);

        currentUser.setWords(currentUser.getWords() + getWordNumber(dbBlog));
        currentUser.setBlogs(currentUser.getBlogs() + 1);
        userRepository.save(currentUser);
        return dbBlog;
    }

    @Override
    public void deleteBlog(String id, String principalName) {
        User currentUser = userRepository.findByUsername(principalName).orElseThrow();
        Blog deletedBlog = blogRepository.findById(id).orElseThrow();
        if (currentUser.getRole().toString().equals("ADMIN") || isUserHasBlog(deletedBlog.getId(), principalName)) {
            blogRepository.deleteById(id);
            currentUser.setWords(currentUser.getWords() - getWordNumber(deletedBlog));
            currentUser.setBlogs(currentUser.getBlogs() - 1);
            userRepository.save(currentUser);
        } else {
            throw new AccessDeniedException("User " + currentUser.getUsername() + " can not delete blog " + deletedBlog.getId());
        }
    }

    private long getWordNumber(Blog blog) {
        String blogText = blog.getBody();
        return blogText.split("\\s").length;
    }

    @Override
    public boolean isUserHasBlog(String blogId, String principalName) {
        User currentUser = userRepository.findByUsername(principalName).orElseThrow();
        Blog deletedBlog = blogRepository.findById(blogId).orElseThrow();
        return currentUser.getRole().toString().equals("ADMIN") ||
                deletedBlog.getAuthor().equals(currentUser.getUsername());
    }
}
