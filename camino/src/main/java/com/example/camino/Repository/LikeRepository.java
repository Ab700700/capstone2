package com.example.camino.Repository;

import com.example.camino.Model.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikePost,Integer> {

    LikePost findLikeByUseridAndPostid(Integer uid, Integer pid);
    List<LikePost> findLikePostsByPostid(Integer pid);

    LikePost findLikePostById(Integer id);
}
