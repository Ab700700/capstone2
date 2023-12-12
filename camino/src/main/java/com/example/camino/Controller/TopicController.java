package com.example.camino.Controller;

import com.example.camino.Model.Topic;
import com.example.camino.Service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camino/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/get")
    public ResponseEntity get(){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopics());
    }

    @PostMapping("/add")
    public ResponseEntity addTopic(@RequestBody @Valid Topic topic, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        topicService.addTopic(topic);
        return ResponseEntity.status(HttpStatus.OK).body("Topic added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTopic(@PathVariable Integer id, @RequestBody @Valid Topic topic, Errors errors){
        if(errors.hasErrors()) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(topicService.updateTopic(id,topic));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTopic(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.deleteTopic(id));
    }

    @GetMapping("/{uid}/{name}")
    public ResponseEntity topicposts(@PathVariable String name,@PathVariable Integer uid){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.topicposts(uid,name));
    }

    @GetMapping("/postsnum/{uid}/{name}")
    public ResponseEntity postsTopicnum(@PathVariable Integer uid,@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.postsTopic(uid,name));
    }


}
