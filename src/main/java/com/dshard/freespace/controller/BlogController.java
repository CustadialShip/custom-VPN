package com.dshard.freespace.controller;

import com.dshard.freespace.auth.AuthenticationService;
import com.dshard.freespace.model.Blog;
import com.dshard.freespace.model.User;
import com.dshard.freespace.persistance.BlogRepository;
import com.dshard.freespace.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {
    Logger logger = LoggerFactory.getLogger(BlogController.class);
    private static final Integer DEFAULT_LIMIT = 10;

    private final BlogRepository blogRepository;

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;

    @GetMapping("/u/{id}")
    private User getUserByUsername(@PathVariable String id) {
        return userRepository.findByUsername(id).get();
    }

    @GetMapping
    private List<Blog> getBlogs() {
        logger.info("getBlogs");
        return blogRepository
                .findAll().stream()
                .filter(blog -> blog.getAccess().equals("public") ||
                                blog.getAccess().equals("private") &&
                                blog.getAuthor().equals(authenticationService.getPrincipalName()))
                .limit(DEFAULT_LIMIT)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private Blog getBlogsById(@PathVariable String id) {
        logger.info("getBlogsById");
        return blogRepository.findById(id).orElse(null);
    }

    @PostMapping
    private String saveBlog(@RequestBody Blog blog) {
        logger.info("saveBlog");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        blog.setAuthor(currentPrincipalName);
        return blogRepository.save(blog).getId();
    }

    @DeleteMapping("/{id}")
    private void deleteBlog(@PathVariable String id) {
        logger.info("deleteBlog");
        blogRepository.deleteById(id);
    }
}
