package com.lria62.QuestionForum.Service;

import com.lria62.QuestionForum.Model.Question;

import com.lria62.QuestionForum.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionService {

    @Autowired
    private  QuestionRepository questionRepository;


    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(int questionId){
        return questionRepository.findByQuestionId(questionId);
    }

    public void delete(Question question){
        questionRepository.delete(question);
    }

    public void save(Question question){
        questionRepository.save(question);
    }

}
