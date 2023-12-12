package com.example.camino.Controller;

import com.example.camino.Model.Comment;
import com.example.camino.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camino/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/get")
    public ResponseEntity getComments(){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments());
    }

    @PostMapping("/add")
    public ResponseEntity addComment(@RequestBody @Valid Comment comment, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        commentService.addCommment(comment);
        return ResponseEntity.status(HttpStatus.OK).body("Comment added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateComment(@RequestBody @Valid Comment comment, @PathVariable Integer id, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id,comment));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(id));
    }

    @PostMapping("/commentto/{cid}/{uid}")
    public ResponseEntity commentto(@PathVariable Integer cid, @PathVariable Integer uid, @RequestBody @Valid Comment comment, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(commentService.comment(cid,uid,comment));
    }

    @GetMapping("/getcommentto/{cid}")
    public ResponseEntity getCommentto(@PathVariable Integer cid){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsto(cid));
    }
}
