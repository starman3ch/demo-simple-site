package com.sandman.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by lch131 on 2017. 4. 18..
 */

@Entity
@Table(name = "Post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_key", nullable = false)
    private long postKey;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "post_body")
    private String postBody;

    @Column(name = "hit")
    private long hit;

//    @Column(name = "write_date")

}
