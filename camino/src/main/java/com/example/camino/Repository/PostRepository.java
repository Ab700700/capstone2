package com.example.camino.Repository;

import com.example.camino.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post,Integer> {

    @Query("select p from Post p where p.userid =?1")
    List<Post> bringPostsById(Integer id);
    List<Post> findPostsByRepostref(Integer repostref);
    Post findPostByUseridAndRepostref(Integer userid,Integer repostref);

    Post findPostById(Integer id);

    @Query("select p from Post p where p.topicid =?1")
    List<Post> bringTopicPosts(Integer id);

    @Query("select avg(p.likes) from Post p")
    Double likesAvg();

    @Query("select count(p) from Post p where p.topicid =?1")
    Integer countPosts(Integer id);
}
