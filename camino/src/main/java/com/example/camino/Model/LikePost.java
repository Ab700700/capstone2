package com.example.camino.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "int not null")
    private Integer postid;
    @Column(columnDefinition = "int not null")
    private Integer userid;
    private Boolean likestatus;
    @Column(columnDefinition = "date not null")
    private LocalDateTime likedate;
}
