package com.example.camino.Service;

import com.example.camino.Api.ApiException;
import com.example.camino.Model.LikePost;
import com.example.camino.Model.Post;
import com.example.camino.Model.User;
import com.example.camino.Repository.LikeRepository;
import com.example.camino.Repository.PostRepository;
import com.example.camino.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    public List<LikePost> getLikes(){
        return likeRepository.findAll();
    }

    public void add(LikePost likePost){
        if(likePost.getPostid() == null || likePost.getUserid()== null) throw new ApiException("postid or userid is null");
        User user = userRepository.findUserById(likePost.getUserid());
        Post post = postRepository.findPostById(likePost.getPostid());
        LikePost oldLikePost = likeRepository.findLikeByUseridAndPostid(likePost.getUserid(), likePost.getPostid());
        if(user == null || post == null) throw new ApiException("User or post not found");
        else if(oldLikePost != null) throw new ApiException("Already there");
        else if(likePost.getLikestatus()==null) throw new ApiException("check the status");
        if(likePost.getLikestatus()){
            if(post.getReposts()>0){
                List<Post> reposts = postRepository.findPostsByRepostref(post.getId());
                for(Post p : reposts){
                    p.setLikes(p.getLikes()+1);
                    postRepository.save(p);
                }
            }
            post.setLikes(post.getLikes()+1);
            postRepository.save(post);
            likePost.setLikedate(LocalDateTime.now());
            likeRepository.save(likePost);
        }else{
            if(post.getReposts()>0){
                List<Post> reposts = postRepository.findPostsByRepostref(post.getId());
                for(Post p : reposts){
                    p.setDislike(p.getDislike()+1);
                    postRepository.save(p);
                }
            }
            post.setDislike(post.getDislike()+1);
            postRepository.save(post);
            likePost.setLikedate(LocalDateTime.now());
            likeRepository.save(likePost);
        }


    }

    public String update(Integer id , LikePost likePost){
        LikePost ol = likeRepository.findLikePostById(id);
        if(ol == null) throw  new ApiException("Like not found");
        else if(likePost.getUserid() == null || likePost.getPostid() == null) throw new ApiException("userid or postid is null");
        Post oldpost = postRepository.findPostById(ol.getPostid());
        User user = userRepository.findUserById(likePost.getUserid());
        Post post= postRepository.findPostById(likePost.getPostid());
        if(user == null || post == null) throw  new ApiException("User or post is not found");
        else if(likePost.getLikestatus()== null) throw  new ApiException("Status is null");
        likePost.setId(id);
        if(ol.getLikestatus()){
            if(oldpost.getReposts()>0){
                List<Post> oldrepost = postRepository.findPostsByRepostref(ol.getPostid());
                for(Post p : oldrepost){
                    p.setLikes(p.getLikes()+1);
                    postRepository.save(p);
                }
                oldpost.setLikes(oldpost.getLikes()-1);
                postRepository.save(oldpost);
            }else{
                oldpost.setLikes(oldpost.getLikes()-1);
                postRepository.save(oldpost);
            }

        }else{
            if(oldpost.getReposts()>0){
                List<Post> oldrepost = postRepository.findPostsByRepostref(ol.getPostid());
                for(Post p : oldrepost){
                    p.setDislike(p.getDislike()-1);
                    postRepository.save(p);
                }
                oldpost.setDislike(oldpost.getDislike()-1);
                postRepository.save(oldpost);
            }else{
                oldpost.setDislike(oldpost.getDislike()-1);
                postRepository.save(oldpost);
            }

        }
        if(likePost.getLikestatus()){
            if(post.getReposts()>0){
                List<Post> repost = postRepository.findPostsByRepostref(likePost.getPostid());
                for(Post p : repost){
                    p.setLikes(p.getLikes()+1);
                    postRepository.save(p);
                }
                post.setLikes(post.getLikes()+1);
                postRepository.save(post);
                likeRepository.save(likePost);
            }else{
                post.setLikes(post.getLikes()+1);
                postRepository.save(post);
                likeRepository.save(likePost);
            }

        }else{
            if(post.getReposts()>0){
                List<Post> repost = postRepository.findPostsByRepostref(likePost.getPostid());
                for(Post p : repost){
                    p.setDislike(p.getDislike()+1);
                    postRepository.save(p);
                }
                post.setDislike(post.getDislike()+1);
                postRepository.save(post);
                likeRepository.save(likePost);
            }else{
                post.setDislike(post.getDislike()+1);
                postRepository.save(post);
                likeRepository.save(likePost);
            }

        }
        return "Like updated";
    }

    public String delete(Integer id){
        LikePost likePost = likeRepository.findLikePostById(id);
        if(likePost == null) throw  new ApiException("Like not found");
        Post post = postRepository.findPostById(likePost.getPostid());
         if(likePost.getLikestatus()){
             if(post.getReposts()>0){
                 List<Post> repost = postRepository.findPostsByRepostref(id);
                 for(Post p : repost){
                     p.setLikes(p.getLikes()-1);
                     postRepository.save(p);
                 }
             }
            post.setLikes(post.getLikes()-1);
            postRepository.save(post);
            likeRepository.delete(likePost);
            return "Like deleted";
        }else{
             if(post.getReposts()>0){
                 List<Post> repost = postRepository.findPostsByRepostref(id);
                 for(Post p : repost){
                     p.setDislike(p.getDislike()-1);
                     postRepository.save(p);
                 }
             }
            post.setDislike(post.getDislike()-1);
            postRepository.save(post);
            likeRepository.delete(likePost);
            return "Like deleted";
        }
    }
}
