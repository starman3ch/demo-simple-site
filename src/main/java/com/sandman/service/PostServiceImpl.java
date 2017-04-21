package com.sandman.service;

import com.sandman.controller.PostRestController;
import com.sandman.domain.Post;
import com.sandman.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by lch131 on 2017. 4. 18..
 */
@Service("postService")
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

//    @Override
//    public Page<Post> findAllPosts(Pageable pageable) {
//        return postRepository.findAll(pageable);
//    }


    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post findPost(int postId) {
        return postRepository.findOne(postId);
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void updatePost(Post post) {
        savePost(post);
    }

    @Override
    public void deletePost(int postId) {
        postRepository.delete(postId);
    }
}
