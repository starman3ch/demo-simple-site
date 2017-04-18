package com.sandman.service;

import com.sandman.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Created by lch131 on 2017. 4. 18..
 */
public interface PostService {

    Page<Post> findAllPosts(Pageable pageable);

    Post findByKey(long postKey);

    void savePost(Post post);

    void updatePost(Post post);

    void deletePostByKey(long postKey);

}
