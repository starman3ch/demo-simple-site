package com.sandman.controller;

import com.sandman.domain.Post;
import com.sandman.service.PostService;
import com.sandman.util.PostErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * Created by lch131 on 2017. 4. 18..
 */
@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    public static final Logger logger = LoggerFactory.getLogger(PostRestController.class);

    @Autowired
    PostService postService;



    // ----------- Posts list -------------
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Post>> listPosts(@PageableDefault Pageable pageable) {
        logger.info("Fetching Posts ");
        Page<Post> posts = postService.findAllPosts(pageable);
//        posts.getSize()//한 페이지당 데이터 수
//        posts.getNumber()//현재 페이지
//        posts.getTotalPages()//전체 페이지
//        posts.getTotalElements()//전체 데이터 수
        posts.getContent().forEach(System.out::println); // Page.getContent로 해당 페이지의 데이터 리스트를 가져올 수 있음.


//        if (!posts.iterator().hasNext()) {
//            return new ResponseEntity(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<Iterable<Post>>(posts, HttpStatus.OK);
    }


    // ----------- Post Detail -------------
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPost(@PathVariable("id") long key) {
        logger.info("Fetching Post with id {}", key);
        Post post = postService.findByKey(key);
        if (post == null) {
            logger.error("Post with id {} not found.", key);
            return new ResponseEntity(new PostErrorType("Post with id " + key + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }



    // ----------- Write Post -------------
    @RequestMapping(value = "write", method = RequestMethod.POST)
    public ResponseEntity<?> writePost(@Validated @RequestBody Post post, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Post : {}", post);

        postService.savePost(post);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(post.getPostKey()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }



    // ----------- Edit Post -------------
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePost(@PathVariable("id") long key, @Validated @RequestBody Post post) {
        logger.info("Updating Post with id {}", key);

        Post currentPost = postService.findByKey(key);

        if (currentPost == null) {
            logger.error("Unable to update. Post with id {} not found.", key);
            return new ResponseEntity(new PostErrorType("Unable to update. Post with id " + key + " not found."), HttpStatus.NOT_FOUND);
        }

        currentPost.setTitle(post.getTitle());
        currentPost.setPostBody(post.getPostBody());

        postService.updatePost(post);
        return new ResponseEntity<Post>(currentPost, HttpStatus.OK);
    }



    // ----------- Delete Post -------------
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePost(@PathVariable("id") long key) {
        logger.info("Deleting Post with id {}", key);

        Post post = postService.findByKey(key);
        if (post == null) {
            logger.error("Unable to delete. Post with id {} not found.", key);
            return new ResponseEntity(new PostErrorType("Unable to delete. Post with id " + key + " not found."), HttpStatus.NOT_FOUND);
        }
        postService.deletePostByKey(key);
        return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
    }
}
