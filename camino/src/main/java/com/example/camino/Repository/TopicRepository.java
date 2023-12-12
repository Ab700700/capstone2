package com.example.camino.Repository;

import com.example.camino.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Integer> {

    Topic findTopicByTopicnameAndUserid(String name, Integer id);
    List<Topic> findTopicsByUserid(Integer id);
}
