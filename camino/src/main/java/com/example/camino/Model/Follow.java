package com.example.camino.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follow {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @NotNull(message = "Followid should not be empty")
        @Column(columnDefinition = "int not null")
        private Integer followid;
        @NotNull(message = "Followerid should not be empty")
        @Column(columnDefinition = "int not null")
        private Integer followerid;
}
