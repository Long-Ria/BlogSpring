package com.lria62.QuestionForum.Repository;

import com.lria62.QuestionForum.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {


    Question findByQuestionId(int questionId);
}
