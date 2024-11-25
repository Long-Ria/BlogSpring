package com.lria62.QuestionForum.Repository;

import com.lria62.QuestionForum.Model.Answer;
import com.lria62.QuestionForum.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {



    @Query("SELECT a FROM Answer a WHERE a.question.questionId = :questionId")
    List<Answer> findByQuestionId(@Param("questionId") int questionId);


    @Query("SELECT a FROM Answer a WHERE a.answerId = :answerId")
    Answer findByAnswerId(@Param("answerId") int answerId);


}

