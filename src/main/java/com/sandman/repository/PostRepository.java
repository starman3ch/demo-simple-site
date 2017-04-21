package com.sandman.repository;

import com.sandman.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Created by lch131 on 2017. 4. 18..
 */
@RepositoryRestResource(collectionResourceRel = "posts", path = "posts")
public interface PostRepository extends /*PagingAndSortingRepository<Post, Integer>*/ JpaRepository<Post, Integer>{
//    Post findByPostKey(@Param("postKey") long postKey);

}
