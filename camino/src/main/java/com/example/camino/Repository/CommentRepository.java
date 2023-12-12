package com.example.camino.Repository;

import com.example.camino.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findCommentsByPostid(Integer postid);
    List<Comment> findCommentsByCommentto(Integer commentid);

    List<Comment> findCommentsByOrderByLikesAsc();
}
