package com.example.camino.Service;

import com.example.camino.Api.ApiException;
import com.example.camino.Model.Comment;
import com.example.camino.Model.Post;
import com.example.camino.Model.User;
import com.example.camino.Repository.CommentRepository;
import com.example.camino.Repository.PostRepository;
import com.example.camino.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    public List<Comment> getComments(){
        return commentRepository.findAll();
    }

    public void addCommment(Comment comment){
        if(comment.getPostid()==null || comment.getUserid() == null) throw new ApiException("postid or userid is null");
        User user  = userRepository.getById(comment.getUserid());
        Post post = postRepository.getById(comment.getPostid());
        if(user == null || post == null) throw new ApiException("User of post not found");
        comment.setLikes(0);
        comment.setCommentto(0);
        post.setComments(post.getComments()+1);
        postRepository.save(post);
        commentRepository.save(comment);
    }


    public String updateComment(Integer id , Comment comment){
        if(comment.getPostid()==null || comment.getUserid() == null) throw new ApiException("postid or userid is null");
        Comment oldcomment = commentRepository.getReferenceById(id);
        if(oldcomment == null) throw new ApiException("Comment not found");
        Post oldpost = postRepository.getReferenceById(oldcomment.getPostid());
        Post post = postRepository.getReferenceById(comment.getPostid());
        if(oldcomment.getPostid()!= comment.getPostid()){
            if(oldpost.getReposts()>0){
                List<Post> reposts =postRepository.findPostsByRepostref(oldpost.getId());
                for(Post p : reposts){
                    p.setComments(p.getComments()-1);
                    postRepository.save(p);
                }
                List<Post> reposts2= postRepository.findPostsByRepostref(post.getId());
                for(Post p: reposts2){
                    p.setComments(p.getComments()+1);
                    postRepository.save(p);
                }
                oldpost.setComments(oldpost.getComments()-1);
                post.setComments(post.getComments()+1);
                postRepository.save(post);
                postRepository.save(oldpost);
                comment.setId(id);
                commentRepository.save(comment);
                return "Comment updated";
            }else{
                oldpost.setComments(oldpost.getComments()-1);
                post.setComments(post.getComments()+1);
                postRepository.save(post);
                postRepository.save(oldpost);
                comment.setId(id);
                commentRepository.save(comment);
                return "Comment updated";
            }

        }else {
            postRepository.save(post);
            comment.setId(id);
            commentRepository.save(comment);
            return "Comment updated";}

    }

    public String deleteComment(Integer id){
        Comment comment = commentRepository.getReferenceById(id);
        if(comment == null) throw new ApiException("Comment not found");
        Post post = postRepository.getReferenceById(comment.getPostid());
        if(post.getReposts()>0){
            List<Post> posts = postRepository.findPostsByRepostref(post.getId());
            for(Post p : posts){
                p.setComments(p.getComments()-1);
                postRepository.save(p);
            }
            post.setComments(post.getComments()-1);
            postRepository.save(post);
            commentRepository.delete(comment);
            return "Comment deleted";
        }else{
            post.setComments(post.getComments()-1);
            postRepository.save(post);
            commentRepository.delete(comment);
            return "Comment deleted";
        }

    }
    //13
    public String comment(Integer cid, Integer uid,Comment comment){
        Comment commentto = commentRepository.getReferenceById(cid);
        Post post = postRepository.getReferenceById(commentto.getPostid());
        User user = userRepository.getReferenceById(uid);
        if(commentto == null || user == null) throw new ApiException("User or commment not found");
        if(post.getReposts()>0){
            List<Post> posts = postRepository.findPostsByRepostref(post.getId());
            for(Post p : posts){
                p.setComments(p.getComments()+1);
                postRepository.save(p);
            }
            comment.setPostid(post.getId());
            comment.setUserid(uid);
            comment.setCommentto(cid);
            comment.setLikes(0);
            post.setComments(post.getComments()+1);
            postRepository.save(post);
            commentRepository.save(comment);
            return "Done";
        }else{
            comment.setPostid(post.getId());
            comment.setUserid(uid);
            comment.setCommentto(cid);
            comment.setLikes(0);
            post.setComments(post.getComments()+1);
            postRepository.save(post);
            commentRepository.save(comment);
            return "Done";
        }



    }
//14
    public List<Comment> getCommentsto(Integer id){
        return commentRepository.findCommentsByCommentto(id);
    }

}
