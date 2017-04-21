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

import java.util.List;


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
/*    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Post>> listPosts(@PageableDefault Pageable pageable) {
        logger.info("Fetching Posts ");
        Page<Post> posts = postService.findAllPosts(pageable);
//        posts.getSize()//한 페이지당 데이터 수
//        posts.getNumber()//현재 페이지
//        posts.getTotalPages()//전체 페이지
//        posts.getTotalElements()//전체 데이터 수
        posts.getContent().forEach(System.out::println); // Page.getContent로 해당 페이지의 데이터 리스트를 가져올 수 있음.
        logger.info("Fetching Posts - getTotalElements: "+posts.getTotalElements());

        if (!posts.iterator().hasNext()) {
            logger.info("Fetching Posts - no_content: ");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Iterable<Post>>(posts, HttpStatus.OK);
    }*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Post>> listPosts() {
        logger.info("Fetching Posts ");
        List<Post> posts = postService.findAllPosts();

        if (!posts.iterator().hasNext()) {
            logger.info("Fetching Posts - no_content: ");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Iterable<Post>>(posts, HttpStatus.OK);
    }


    // ----------- Post Detail -------------
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPost(@PathVariable("id") int id) {
        logger.info("Fetching Post with id {}", id);
        Post post = postService.findPost(id);
        if (post == null) {
            logger.error("Post with id {} not found.", id);
            return new ResponseEntity(new PostErrorType("Post with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }



    // ----------- Write Post -------------
    @RequestMapping(value = "write", method = RequestMethod.POST)
    public ResponseEntity<?> writePost(@Validated @RequestBody Post post, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Post : {}", post);

        postService.savePost(post);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(post.getPostId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED); // 내려주는 데이터가 없음.. 그냥 상태값만 내려가는 듯..
    }



    // ----------- Edit Post -------------
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePost(@PathVariable("id") int id, @Validated @RequestBody Post post) {
        logger.info("Updating Post with id {}", id);

        Post currentPost = postService.findPost(id);

        if (currentPost == null) {
            logger.error("Unable to update. Post with id {} not found.", id);
            return new ResponseEntity(new PostErrorType("Unable to update. Post with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }

        currentPost.setTitle(post.getTitle());
        currentPost.setContent(post.getContent());

        postService.updatePost(currentPost);
        return new ResponseEntity<Post>(currentPost, HttpStatus.OK);
    }



    // ----------- Delete Post -------------
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePost(@PathVariable("id") int id) {
        logger.info("Deleting Post with id {}", id);

        Post post = postService.findPost(id);
        if (post == null) {
            logger.error("Unable to delete. Post with id {} not found.", id);
            return new ResponseEntity(new PostErrorType("Unable to delete. Post with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        postService.deletePost(id);
        return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
    }
}
