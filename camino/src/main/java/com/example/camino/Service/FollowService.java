package com.example.camino.Service;

import com.example.camino.Api.ApiException;
import com.example.camino.Model.Follow;
import com.example.camino.Model.User;
import com.example.camino.Repository.FollowRepository;
import com.example.camino.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public List<Follow> getFollows(){
        return followRepository.findAll();
    }

    public void add(Follow follow){
        User user = userRepository.getById(follow.getFollowid());
        User user1 = userRepository.getById(follow.getFollowerid());
        if(user == null || user1 == null) throw new ApiException("One of the users not found");
        else if(user.equals(user1)) throw new ApiException("Can't follow himself");
        user.setFollowers(user.getFollowers()+1);
        user1.setFollow(user1.getFollow()+1);
        userRepository.save(user);
        userRepository.save(user1);
        followRepository.save(follow);
    }

    public String update(Integer id, Follow follow){
        Follow follow1= followRepository.getById(id);
        User user = userRepository.getById(follow.getFollowid());
        User user1 = userRepository.getById(follow.getFollowerid());
        if(follow1 == null) throw new ApiException("Follow not found");
        else if(user == null || user1 == null) throw  new ApiException("One of the users not found");
        else if(user.equals(user1)) throw  new ApiException("Can't follow himself");
        User oldUser = userRepository.getById(follow1.getFollowid());
        User oldUser1 = userRepository.getById(follow1.getFollowerid());
        user.setFollowers(user.getFollowers()+1);
        user1.setFollow(user1.getFollow()+1);
        oldUser.setFollowers(oldUser.getFollowers()-1);
        oldUser1.setFollow(oldUser1.getFollow()-1);
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(oldUser);
        userRepository.save(oldUser1);
        follow.setId(id);
        followRepository.save(follow);
        return "Follow Updated";
    }

    public String delete(Integer id){
        Follow follow = followRepository.getById(id);
        if(follow == null) throw new ApiException("Follow not found");
        followRepository.delete(follow);
        User user = userRepository.getById(follow.getFollowerid());
        User user1 = userRepository.getById(follow.getFollowid());
        user.setFollow(user.getFollow()-1);
        user1.setFollowers(user1.getFollowers()-1);
        userRepository.save(user);
        userRepository.save(user1);
        return "Follow deleted";
    }
//8
    public String follow(Integer uid, Integer fid){
        User user = userRepository.getById(uid);
        User user1 = userRepository.getById(fid);
        Follow follow = followRepository.findFollowByFolloweridAndFollowid(uid,fid);
        if(user == null|| user1== null) throw new ApiException("One of the users not found");
        else if(user.equals(user1)) throw new ApiException("They're same user");
        else if(follow != null) throw new ApiException("Already there");
        Follow newF = new Follow();
        newF.setFollowerid(uid);
        newF.setFollowid(fid);
        followRepository.save(newF);
        user.setFollow(user.getFollow()+1);
        user1.setFollowers(user1.getFollowers()+1);
        userRepository.save(user);
        userRepository.save(user1);
        return uid+" follow "+fid;
    }
//9
    public String unfollow(Integer uid, Integer fid){
        User user = userRepository.getById(uid);
        User user1 = userRepository.getById(fid);
        if(user == null || user1 == null) throw  new ApiException("One of the users not found");
        else if(user.equals(user1)) throw  new ApiException("Can't unfollow himself");
        Follow follow = followRepository.findFollowByFolloweridAndFollowid(uid,fid);
        if(follow == null) throw new ApiException("User don't follow him");
        user.setFollow(user.getFollow()-1);
        user1.setFollowers(user1.getFollowers()-1);
        userRepository.save(user);
        userRepository.save(user1);
        followRepository.delete(follow);
        return uid+" unfollow "+fid;
    }



}
