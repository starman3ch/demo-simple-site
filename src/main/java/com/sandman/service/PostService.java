package com.sandman.service;

import com.sandman.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created by lch131 on 2017. 4. 18..
 */
public interface PostService {

//    Page<Post> findAllPosts(Pageable pageable);
    List<Post> findAllPosts();

    Post findPost(int postId);

    void savePost(Post post);

    void updatePost(Post post);

    void deletePost(int postId);

}
