package com.sandman.service;

import com.sandman.domain.Post;

import java.awt.print.Pageable;


/**
 * Created by lch131 on 2017. 4. 18..
 */
public interface PostService {

    Iterable<Post> findAllPosts(Pageable pageable);

    Post findByKey(long postKey);

    void savePost(Post post);

    void updatePost(Post post);

    void deletePostByKey(long postKey);

}
