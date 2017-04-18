package com.sandman.controller;

import com.sandman.domain.Post;
import com.sandman.form.PostForm;
import com.sandman.service.PostService;
import com.sandman.util.PostErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * Created by lch131 on 2017. 4. 18..
 */
@Controller
@RequestMapping("/posts")
public class PostController {

    public static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;


    @ModelAttribute
    PostForm setUpPostForm() {
        return new PostForm();
    }

    // ----------- Posts list -------------
    @RequestMapping(method = RequestMethod.GET)
    public String listPosts(@PageableDefault Pageable pageable, Model model) {
        logger.info("Fetching Posts ");
        Page<Post> posts = postService.findAllPosts(pageable);
//        posts.getSize()//한 페이지당 데이터 수
//        posts.getNumber()//현재 페이지
//        posts.getTotalPages()//전체 페이지
//        posts.getTotalElements()//전체 데이터 수
        posts.getContent().forEach(System.out::println); // Page.getContent로 해당 페이지의 데이터 리스트를 가져올 수 있음.

        model.addAttribute("posts", posts);
        return "posts/list";
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
    public String writePost(@Validated PostForm form, BindingResult result, Model model) {
        logger.info("Creating Post");
        if (result.hasErrors()) {
//            return listPosts(model);
            return "redirect:/posts";
        }

        Post post = new Post();
        BeanUtils.copyProperties(form, post);

        postService.savePost(post);

//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(post.getPostKey()).toUri());
//        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        return "redirect:/posts";
    }



    // ----------- Edit Post -------------
    @RequestMapping(value = "edit", params = "form", method = RequestMethod.GET)
    public String editForm(@RequestParam long key, PostForm form) {
        logger.info("Updating Post with id {}", key);

        Post post = postService.findByKey(key);
        BeanUtils.copyProperties(form, post);
        return "posts/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String edit(@RequestParam long key, @Validated PostForm form, BindingResult result) {
        if (result.hasErrors()) {
            return editForm(key, form);
        }

        Post post = new Post();
        BeanUtils.copyProperties(form, post);
        post.setPostKey(key);
        postService.updatePost(post);
        return "redirect:/posts";
    }

    @RequestMapping(value = "edit", params = "goToList")
    public String goToList() {
        return "redirect:/posts";
    }



    // ----------- Delete Post -------------
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam long key) {
        logger.info("Deleting Post with id {}", key);

//        Post post = postService.findByKey(key);
//        if (post == null) {
//            logger.error("Unable to delete. Post with id {} not found.", key);
//            return new ResponseEntity(new PostErrorType("Unable to delete. Post with id " + key + " not found."), HttpStatus.NOT_FOUND);
//        }
        postService.deletePostByKey(key);
        return "redirect:/posts";
    }
}
