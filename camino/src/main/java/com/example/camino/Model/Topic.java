package com.example.camino.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "topic name should not be empty")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String topicname;
    @NotNull(message = "userid should not be emtpy")
    @Column(columnDefinition = "int not null")
    private Integer userid;

}
