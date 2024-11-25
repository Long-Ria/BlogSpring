package com.lria62.QuestionForum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    private String roleName;

    public Role(){

    }

    public Role(int roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<User> users;

    // Getters and Setters
}
