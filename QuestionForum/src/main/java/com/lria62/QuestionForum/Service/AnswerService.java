package com.lria62.QuestionForum.Service;



import com.lria62.QuestionForum.Model.Answer;
import com.lria62.QuestionForum.Model.Question;
import com.lria62.QuestionForum.Repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;



    // Phương thức lưu một Answer mới
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    // Phương thức xóa một Answer
    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    // Phương thức xóa tất cả các Answer liên quan đến một Question
    public void deleteAll(List<Answer> answers) {
        answerRepository.deleteAll(answers);
    }

    // Phương thức tìm Answer theo ID
    public Optional<Answer> getAnswerById(int id) {
        return answerRepository.findById(id);
    }

    public Answer getAnswerByAnswerId(int answerId){
        return answerRepository.findByAnswerId(answerId);
    }

    // Phương thức tìm tất cả Answer của một Question
    public List<Answer> getAnswersByQuestion(int questionId) {
        return answerRepository.findByQuestionId(questionId);
    }
}
