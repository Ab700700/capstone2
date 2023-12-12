package com.example.camino.Controller;

import com.example.camino.Model.Post;
import com.example.camino.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camino/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/get")
    public ResponseEntity getPosts(){
        return  ResponseEntity.status(HttpStatus.OK).body(postService.getPosts());
    }

    @PostMapping("/add/{uid}")
    public ResponseEntity addPost(@PathVariable Integer uid, @RequestBody @Valid Post post, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(postService.addPost(uid,post));
    }

    @PutMapping("/update/{id}/{uid}")
    public ResponseEntity updatePost(@PathVariable Integer id , @PathVariable Integer uid,@RequestBody @Valid Post post , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(id,uid,post));
    }

    @DeleteMapping("/delete/{uid}/{pid}")
    public ResponseEntity delete(@PathVariable Integer uid, @PathVariable Integer pid){
        return ResponseEntity.status(HttpStatus.OK).body(postService.delete(uid,pid));
    }

    @PutMapping("/repost/{id}/{uid}")
    public ResponseEntity repost(@PathVariable Integer id, @PathVariable  Integer uid){
        return ResponseEntity.status(HttpStatus.OK).body(postService.repost(id,uid));
    }

    @GetMapping("/postfollow/{uid}")
    public ResponseEntity postfollows(@PathVariable Integer uid){
        return ResponseEntity.status(HttpStatus.OK).body(postService.postfollows(uid));
    }

    @GetMapping("/getcomments/{pid}")
    public ResponseEntity getcomments(@PathVariable Integer pid){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getComments(pid));
    }
    @GetMapping("/likes/{pid}")
    public ResponseEntity likes(@PathVariable Integer pid){
        return ResponseEntity.status(HttpStatus.OK).body(postService.likes(pid));
    }
    @GetMapping("/postsofuser/{id}")
    public ResponseEntity postsOfUser(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.postsOfuser(id));
    }

    @GetMapping("/reposts/{pid}")
    public ResponseEntity reposts(@PathVariable Integer pid){
        return ResponseEntity.status(HttpStatus.OK).body(postService.reposts(pid));
    }

    @GetMapping("/explore")
    public ResponseEntity explore(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.explore());
    }
}
