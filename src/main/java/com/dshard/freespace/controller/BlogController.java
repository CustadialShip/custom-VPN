package com.dshard.freespace.controller;

import com.dshard.freespace.auth.AuthenticationService;
import com.dshard.freespace.model.Blog;
import com.dshard.freespace.model.RequestFormBlog;
import com.dshard.freespace.model.ResponseBlogList;
import com.dshard.freespace.persistance.BlogRepository;
import com.dshard.freespace.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs")
public class BlogController {
    Logger logger = LoggerFactory.getLogger(BlogController.class);

    private final BlogRepository blogRepository;
    private final AuthenticationService authenticationService;
    private final BlogService blogService;

    @GetMapping
    private ResponseBlogList getBlogs(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int size) {
        try {
            Pageable paging = PageRequest.of(page - 1, size);
            String currentPrincipalName = authenticationService.getPrincipalName();
            Page<Blog> pageBlogs = blogService.getBlogsByUserAccessAndPagination(currentPrincipalName, paging);
            return ResponseBlogList.builder()
                    .blogs(pageBlogs.getContent())
                    .currentPage(pageBlogs.getNumber())
                    .totalItems(pageBlogs.getTotalElements())
                    .totalPage(pageBlogs.getTotalPages())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/{id}")
    private Blog getBlogById(@PathVariable String id) {
        logger.info("getBlogsById");
        return blogRepository.findById(id).orElse(null);
    }

    @PostMapping
    private String saveBlog(@RequestBody RequestFormBlog requestFormBlog) {
        logger.info("saveBlog");
        return blogService.saveBlog(requestFormBlog, authenticationService.getPrincipalName()).getId();
    }

    @DeleteMapping("/{id}")
    private void deleteBlog(@PathVariable String id) {
        logger.info("deleteBlog");
        blogService.deleteBlog(id, authenticationService.getPrincipalName());
    }

    @GetMapping("/{id}/me")
    private boolean isUserHasBlog(@PathVariable String id) {
        logger.info("getBlogsById");
        return blogService.isUserHasBlog(id, authenticationService.getPrincipalName());
    }
}
