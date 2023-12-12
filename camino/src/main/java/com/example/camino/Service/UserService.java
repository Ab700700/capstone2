package com.example.camino.Service;

import com.example.camino.Api.ApiException;
import com.example.camino.Model.Follow;
import com.example.camino.Model.Topic;
import com.example.camino.Model.User;
import com.example.camino.Repository.FollowRepository;
import com.example.camino.Repository.TopicRepository;
import com.example.camino.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final TopicRepository topicRepository;

    public List<User> getUesrs(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        user.setFollowers(0);
        user.setFollow(0);
        user.setRegisterdate(LocalDateTime.now());
        userRepository.save(user);
    }

    public String updateUser(Integer uid, String pass , User user){
        User u = userRepository.getById(uid);
        if(u == null) throw new ApiException("User not found");
        else if(!u.getPassword().equals(pass)) throw new ApiException("Password wrong");

        user.setId(uid);
        user.setRegisterdate(u.getRegisterdate());
        user.setFollowers(u.getFollowers());
        user.setFollow(u.getFollow());
        user.setPassword(u.getPassword());
        userRepository.save(user);
        return "User updated";
    }

    public String deleteUser(Integer uid, String  pass){
        User user = userRepository.getById(uid);
        if(user == null) throw new ApiException("User not found");
        else if(!user.getPassword().equals(pass)) throw new ApiException("Password is wrong");
        userRepository.delete(user);
        return "User deleted";
    }

    public List<User> follow(Integer id){
        User user = userRepository.getById(id);
        if(user == null) throw new ApiException("User not found");
        List<Follow> follows = followRepository.bringFollow(id);
        List<User> users = new ArrayList<>();
        for(Follow f: follows){
            users.add(userRepository.getById(f.getFollowid()));
        }
        return users;
    }

    public List<User> followers(Integer id){
        User user = userRepository.getById(id);
        if(user == null) throw new ApiException("User not found");
        List<Follow> followers = followRepository.bringFollowers(id);
        List<User> users = new ArrayList<>();
        for(Follow f : followers){
            users.add( userRepository.getReferenceById(f.getFollowerid()));
        }
        return users;
    }

    public List<User> searchStartWith(String w){
        return userRepository.findUsersByUsernameStartingWith(w);
    }

    public List<User> searchByBirthDateBetween(Date date1,Date date2 ){
        return userRepository.findUsersByBirthdateBetween(date1,date2);
    }
    public List<User> searchAfterBirthDate(Date date){
        return userRepository.findUsersByBirthdateAfter(date);
    }

    public User searchByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public List<Topic> getUserTopics(Integer uid){
        User user = userRepository.findUserById(uid);
        if(user == null) throw  new ApiException("User not found");
        return topicRepository.findTopicsByUserid(uid);
    }

}
