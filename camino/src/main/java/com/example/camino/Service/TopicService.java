package com.example.camino.Service;

import com.example.camino.Api.ApiException;
import com.example.camino.Model.Post;
import com.example.camino.Model.Topic;
import com.example.camino.Model.User;
import com.example.camino.Repository.PostRepository;
import com.example.camino.Repository.TopicRepository;
import com.example.camino.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public List<Topic> getTopics(){
        return topicRepository.findAll();
    }

    public void addTopic(Topic topic){
        topicRepository.save(topic);
    }

    public String updateTopic(Integer id, Topic topic){
        Topic topic1 = topicRepository.getReferenceById(id);
        if(topic1 == null) throw  new ApiException("Topic not found");
        topic.setId(id);
        topicRepository.save(topic);
        return "Topic updated";
    }

    public String deleteTopic(Integer id){
        Topic topic = topicRepository.getReferenceById(id);
        if(topic == null) throw new ApiException("Topic not found");
        topicRepository.delete(topic);
        return "Topic deleted";
    }

    public List<Post> topicposts(Integer uid,String name){
        User user = userRepository.findUserById(uid);
        if(user == null) throw  new ApiException("User not found");
        Topic topic = topicRepository.findTopicByTopicnameAndUserid(name,uid);
        if(topic == null) throw new ApiException("Topic not found");
        List<Post> posts = postRepository.bringTopicPosts(topic.getId());
        return posts;
    }

    public Integer postsTopic(Integer uid,String name){
        User user = userRepository.findUserById(uid);
        if(user == null) throw new ApiException("User not found");
        List<Post> posts = this.topicposts(uid,name);
        return posts.size();
    }
}
