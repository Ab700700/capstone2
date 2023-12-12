package com.example.camino.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(50)")
    private String media;
    @Column(columnDefinition = "varchar(250)")
    private String textcontent;
    @NotNull(message = "User id must not be empty")
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer userid;
    @Column(columnDefinition = "int ")
    private Integer topicid;
    @Column(columnDefinition = "int not null")
    private Integer comments;
    @Column(columnDefinition = "int not null")
    private Integer likes;
    @Column(columnDefinition = "int not null")
    private Integer dislike;
    @Column(columnDefinition = "int not null")
    private Integer repostref;
    @Column(columnDefinition = "int not null")
    private Integer reposts;
    @Column(columnDefinition = "date not null")
    private LocalDateTime postdate;
    @Column(columnDefinition = "boolean not null")
    private Boolean edit;
    private LocalDateTime lastedit;

}
