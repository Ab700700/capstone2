package com.example.camino.Service;

import com.example.camino.Api.ApiException;
import com.example.camino.Model.*;
import com.example.camino.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final TopicRepository topicRepository;
    private final UserService userService;
    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    public String addPost(Integer id,Post post){
        User user = userRepository.findUserById(id);
        if(user == null) throw new ApiException("User not found");
        else if(post.getMedia() == null && post.getTextcontent() == null) return "You must write something to post it";
        post.setUserid(id);
        post.setDislike(0);
        post.setComments(0);
        post.setRepostref(0);
        post.setReposts(0);
        post.setLikes(0);
        post.setEdit(false);
        post.setPostdate(LocalDateTime.now());
        postRepository.save(post);
        return "Posted";
    }

    public String update(Integer id, Integer uid, Post post){
        User user  = userRepository.findUserById(uid);
        Post oldPost = postRepository.findPostById(id);
        if(user == null || oldPost == null) throw  new ApiException("User or post not found");
        else if(oldPost.getUserid() != uid) throw  new ApiException("User does not have a permission to update the post");
        else if(post.getTextcontent()==null && post.getMedia() == null) return "You must write something to update the post";
        else if(oldPost.getMedia()==post.getMedia() && oldPost.getTextcontent()==post.getTextcontent()) return "You didn't write anything new";

        if(oldPost.getReposts()>0){
            List<Post> reposts = postRepository.findPostsByRepostref(post.getId());
            for(Post p: reposts){
                p.setId(id);
                p.setLikes(oldPost.getLikes());
                p.setDislike(oldPost.getDislike());
                p.setComments(oldPost.getComments());
                p.setReposts(oldPost.getReposts());
                p.setRepostref(oldPost.getRepostref());
                p.setPostdate(oldPost.getPostdate());
                p.setEdit(true);
                p.setLastedit(LocalDateTime.now());
                postRepository.save(p);

            }
            post.setId(id);
            post.setLikes(oldPost.getLikes());
            post.setDislike(oldPost.getDislike());
            post.setComments(oldPost.getComments());
            post.setReposts(oldPost.getReposts());
            post.setRepostref(oldPost.getRepostref());
            post.setPostdate(oldPost.getPostdate());
            post.setEdit(true);
            post.setLastedit(LocalDateTime.now());
            postRepository.save(post);
            return "Post updated";
        }else{
            post.setId(id);
            post.setLikes(oldPost.getLikes());
            post.setDislike(oldPost.getDislike());
            post.setComments(oldPost.getComments());
            post.setReposts(oldPost.getReposts());
            post.setRepostref(oldPost.getRepostref());
            post.setPostdate(oldPost.getPostdate());
            post.setEdit(true);
            post.setLastedit(LocalDateTime.now());
            postRepository.save(post);
            return "Post updated";
        }

    }

    public String delete(Integer uid, Integer pid){
        User user = userRepository.findUserById(uid);
        Post post = postRepository.findPostById(pid);
        if(user == null || post == null) throw  new ApiException("User or post is not found");
        else if(!post.getUserid().equals(uid)) throw  new ApiException("User does not have a permission to delete the post");
        else if(post.getReposts()>0){
                List<Post> reposts = postRepository.findPostsByRepostref(post.getId());
                for(Post p: reposts){
                    postRepository.delete(p);
                }
            postRepository.delete(post);
                return "Post deleted";
        }
        postRepository.delete(post);
        return "Post deleted";
    }

    public String repost(Integer id, Integer uid){
        Post post = postRepository.findPostById(id);
        Post Isreposted = postRepository.findPostByUseridAndRepostref(uid,id);
        User user = userRepository.findUserById(uid);
       // String fakedate="2023-11-30T21:00:00";
        Post repost = new Post();
        if(user == null) return "User not found";
        else if(post == null) return "Post not found";
        else if(Isreposted != null) return "Your already repost it";
        post.setReposts(post.getReposts()+1);
        postRepository.save(post);
        repost.setUserid(uid);
        repost.setEdit(post.getEdit());
        repost.setLikes(post.getLikes());
        repost.setTopicid(post.getTopicid());
        repost.setReposts(post.getReposts());
        repost.setMedia(post.getMedia());
        repost.setDislike(post.getDislike());
        repost.setComments(post.getComments());
        repost.setRepostref(id);
        repost.setTextcontent(post.getTextcontent());
        repost.setLastedit(post.getLastedit());
        //repost.setPostdate(Date.valueOf(fakedate));
       repost.setPostdate(LocalDateTime.now());
        postRepository.save(repost);
        return "Reposted";

    }

    public List<Comment> getComments(Integer pid){
        Post post = postRepository.findPostById(pid);
        if(post == null) throw new ApiException("Post not found");
        return commentRepository.findCommentsByPostid(pid);
    }

    public List<User> likes(Integer pid){
        Post post = postRepository.findPostById(pid);
        if(post == null) throw new ApiException("Post not found");
        List<User> users = new ArrayList<>();
        List<LikePost> likes = likeRepository.findLikePostsByPostid(pid);
        for(LikePost l:likes){
            if(l.getLikestatus()){
                User user = userRepository.findUserById(l.getUserid());
                users.add(user);
            }
        }
        return users;
    }

    public List<Post> postsOfuser(Integer id){
        return postRepository.bringPostsById(id);
    }

    public List<User> reposts(Integer pid){
        Post post = postRepository.findPostById(pid);
        if(post == null) throw new ApiException("Post not found");
        List<Post> reposts = postRepository.findPostsByRepostref(pid);
        List<User> users = new ArrayList<>();
        for(Post p:reposts){
            User user = userRepository.findUserById(p.getUserid());
            users.add(user);
        }
        return users;
    }

    public List<Post> postfollows(Integer id){
        User user = userRepository.findUserById(id);
        if(user == null ) throw new ApiException("User not found");
        List<User> users = userService.follow(id);
        List<Post> posts = new ArrayList<>();
        for(User u : users){
            List<Post> upost = postRepository.bringPostsById(u.getId());
            for(Post p : upost){
                posts.add(p);
            }
        }
        posts.sort(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return o1.getPostdate().compareTo(o2.getPostdate());
            }
        });
        Collections.reverse(posts);
        return posts;
    }


    public List<Post> explore(){
        Double avg = postRepository.likesAvg();
        List<Post> posts = postRepository.findAll();
        List<Post> top = new ArrayList<>();
        for(Post p : posts){
            if(p.getLikes()>avg) top.add(p);
        }
        return top;
    }


}
