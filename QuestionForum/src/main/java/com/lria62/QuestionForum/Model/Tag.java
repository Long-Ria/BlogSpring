package com.lria62.QuestionForum.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Question> questions;

    // Getters and Setters
}
