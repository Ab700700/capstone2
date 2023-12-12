package com.example.camino.Repository;

import com.example.camino.Model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {

    Follow findFollowByFolloweridAndFollowid(Integer followerid, Integer followid);
    @Query("select f from Follow f where f.followid =?1")
    List<Follow> bringFollowers(Integer uid);

    @Query("select f from Follow f where f.followerid =?1")
    List<Follow> bringFollow(Integer uid);
}
