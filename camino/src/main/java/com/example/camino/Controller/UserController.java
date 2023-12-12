package com.example.camino.Controller;

import com.example.camino.Model.User;
import com.example.camino.Service.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/camino/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUesrs());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        userService.addUser(user);
        return  ResponseEntity.status(HttpStatus.OK).body("User added");
    }

    @PutMapping("/update/{id}/{pass}")
    public ResponseEntity updateUser(@PathVariable Integer id , @PathVariable String pass , @RequestBody User user,Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id,pass,user));
    }

    @DeleteMapping("/delete/{id}/{pass}")
    public ResponseEntity deleteUser(@PathVariable Integer id , @PathVariable String pass){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id,pass));
    }

    @GetMapping("/follows/{id}")
    public ResponseEntity follows(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.follow(id));
    }

    @GetMapping("/followers/{id}")
    public ResponseEntity followers(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.followers(id));
    }

    @GetMapping("/search/{w}")
    public ResponseEntity search(@PathVariable String w){
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchStartWith(w));
    }

    @GetMapping("/searchbetween/{date1}/{date2}")
    public ResponseEntity searchByBirthDateBetween(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2){
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchByBirthDateBetween(date1,date2));
    }

    @GetMapping("/searchafter/{date}")
    public ResponseEntity searchAfterBirthDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchAfterBirthDate(date));
    }

    @GetMapping("/searchemail/{email}")
    public ResponseEntity searchByEmail(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchByEmail(email));
    }

    @GetMapping("/usertopics/{uid}")
    public ResponseEntity getUserTopics(@PathVariable Integer uid){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserTopics(uid));
    }
}
