package com.example.camino.Controller;

import com.example.camino.Model.LikePost;
import com.example.camino.Service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camino/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/get")
    public ResponseEntity getLikes(){
        return ResponseEntity.status(HttpStatus.OK).body(likeService.getLikes());
    }

    @PostMapping("/add")
    public ResponseEntity addLike(@RequestBody @Valid LikePost likePost, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        likeService.add(likePost);
        return ResponseEntity.status(HttpStatus.OK).body("Like adde");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateLike(@RequestBody @Valid LikePost likePost, @PathVariable Integer id, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(likeService.update(id, likePost));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(likeService.delete(id));
    }
}
