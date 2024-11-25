package com.lria62.QuestionForum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;
    private String title;
    @Lob
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "QuestionTag",
            joinColumns = @JoinColumn(name = "questionId"),
            inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private List<Tag> tags;

    @Override
    public String toString() {
        return "Question [questionId=" + questionId + ", title=" + title + ", createdDate=" + createdDate + ", user=" + (user != null ? user.getUserId() : "null") + "]";
    }
}
