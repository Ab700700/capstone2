package com.example.camino.Repository;

import com.example.camino.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findUsersByUsernameStartingWith(String w);
    User findUserById(Integer userid);

    List<User> findUsersByBirthdateBetween(Date datetime, Date todatetime);
    List<User> findUsersByBirthdateAfter(Date dateTime);

    User findUserByEmail(String email);
}
