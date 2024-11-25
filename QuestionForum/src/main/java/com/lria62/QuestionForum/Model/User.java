package com.lria62.QuestionForum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String username;

    private String password;

    private String email;

    private Boolean gender;

    @Lob
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Question> questions;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Answer> answers;

    // Getters and Setters
}

