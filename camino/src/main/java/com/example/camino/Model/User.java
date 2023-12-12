package com.example.camino.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 4,message = "Username should be at least 4 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+",message = "Username should only be letters and numbers")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username;
    @Email(message = "Write a valid email")
    @NotEmpty(message = "Email should not be empty")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;
    @NotNull(message = "Birth date should not be empty")
    @DateTimeFormat(pattern = "yyyy/mm/dd")
    @Column(columnDefinition = "date not null")
    private Date birthdate;
    @Column(columnDefinition = "varchar(50)")
    private String profileimage;
    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8,message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*#?&]{8,}$",message = "Password should contains one capital letter and a special character and a number")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;
    @Column(columnDefinition = "varchar(60)")
    private String bio;
    @Column(columnDefinition = "int not null")
    private Integer follow;
    @Column(columnDefinition = "int not null")
    private Integer followers;
    @Column(columnDefinition = "date not null")
    private LocalDateTime registerdate;
}
