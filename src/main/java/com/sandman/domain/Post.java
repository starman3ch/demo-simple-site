package com.sandman.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by lch131 on 2017. 4. 18..
 */

//@Data
@Entity
@Table(name = "Post")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id", nullable = false)
    private int postId;

    @NotNull
    @Size(min = 1, max = 125)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

//    @Column(name = "hit")
//    private long hit;

//    @Column(name = "write_date")


    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
