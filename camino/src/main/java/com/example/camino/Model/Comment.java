package com.example.camino.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "int not null")
    private Integer postid;
    @Column(columnDefinition = "int not null")
    private Integer userid;
    @Column(columnDefinition = "int not null")
    private Integer likes;
    @Column(columnDefinition = "int not null")
    private Integer commentto;
    @NotEmpty(message = "Comment should not be empty")
    @Column(columnDefinition = "varchar(150) not null")
    private String textcontent;

}
