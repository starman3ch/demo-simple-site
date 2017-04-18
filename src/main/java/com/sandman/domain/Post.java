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

    @NotNull
    @Size(min = 1, max = 125)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Size(min = 1)
    @Column(name = "post_body")
    private String postBody;

    @Column(name = "hit")
    private long hit;

//    @Column(name = "write_date")

}
