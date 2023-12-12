package com.example.camino.Controller;

import com.example.camino.Model.Follow;
import com.example.camino.Service.FollowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camino/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/get")
    public ResponseEntity gerAllFollows(){
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollows());
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Follow follow, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        followService.add(follow);
        return ResponseEntity.status(HttpStatus.OK).body("Follow added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateFollow(@PathVariable Integer id,@RequestBody @Valid Follow follow, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(followService.update(id,follow));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFollow(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(followService.delete(id));
    }

    @PostMapping("/follow/{id}/{fid}")
    public ResponseEntity follow(@PathVariable Integer id, @PathVariable Integer fid){
        return ResponseEntity.status(HttpStatus.OK).body(followService.follow(id,fid));
    }

    @DeleteMapping("/unfollow/{id}/{fid}")
    public ResponseEntity unfollow(@PathVariable Integer id, @PathVariable Integer fid){
        return ResponseEntity.status(HttpStatus.OK).body(followService.unfollow(id,fid));
    }
}
