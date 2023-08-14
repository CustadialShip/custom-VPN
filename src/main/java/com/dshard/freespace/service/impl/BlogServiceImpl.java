package com.dshard.freespace.service.impl;

import com.dshard.freespace.model.Blog;
import com.dshard.freespace.model.RequestFormBlog;
import com.dshard.freespace.model.User;
import com.dshard.freespace.persistance.BlogRepository;
import com.dshard.freespace.persistance.UserRepository;
import com.dshard.freespace.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Override
    public Blog saveBlog(RequestFormBlog requestFormBlog, String principalName) {
        User currentUser = userRepository.findByUsername(principalName).orElseThrow();
        Blog blog = Blog.builder()
                .body(requestFormBlog.getBody())
                .author(principalName)
                .access(requestFormBlog.getAccess())
                .title(requestFormBlog.getTitle())
                .posted(LocalDateTime.now())
                .build();
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

    @Override
    public Page<Blog> getBlogsByUserAccessAndPagination(String principalName, Pageable pageable) {
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("access").is("public"), Criteria.where("author").is(principalName));

        Query query = new Query();
        query.addCriteria(criteria);
        long totalCount = mongoTemplate.count(query, Blog.class);

        query.with(pageable);
        List<Blog> result = mongoTemplate.find(query, Blog.class);

        return new PageImpl<>(result, pageable, totalCount);
    }
}
