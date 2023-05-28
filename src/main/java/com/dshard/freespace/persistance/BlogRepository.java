package com.dshard.freespace.persistance;

import com.dshard.freespace.model.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog, String> {
}
