package com.lria62.QuestionForum.Controller;



import com.lria62.QuestionForum.DTO.AnswerDTO;
import com.lria62.QuestionForum.Model.Answer;
import com.lria62.QuestionForum.Model.Question;
import com.lria62.QuestionForum.Service.AnswerService;
import com.lria62.QuestionForum.Service.QuestionService;
import com.lria62.QuestionForum.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answers")
public class AnswerRestController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    // Get answers by QuestionId
    @GetMapping("/{questionId}")
    public ResponseEntity<?> getAnswersByQuestionId(@PathVariable int questionId) {

        if (questionId <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

//        Question question = questionService.getQuestionById(questionId);
//        if(question == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
        List<Answer> answers = answerService.getAnswersByQuestion(questionId);

        List<Map<String, String>> result = answers
                .stream()
                .map(answer -> {
                    Map<String, String> answerMap = new HashMap<>();
                    answerMap.put("Username", answer.getUser().getUsername());
                    answerMap.put("CreatedAt", getTimeElapsed(convertToLocalDateTime(answer.getCreatedAt())));
                    answerMap.put("Content", answer.getContent());
                    return answerMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // Insert answer
    @PostMapping
    public ResponseEntity<Answer> insertAnswer(@RequestBody AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        Answer newAnswer = new Answer();

        if(userService.getUserById(answerDTO.getUserId()) == null){
            return ResponseEntity.badRequest().build();
        }

        newAnswer.setUser(userService.getUserById(answerDTO.getUserId()));

        if(questionService.getQuestionById(answerDTO.getQuestionId()) == null){
            return ResponseEntity.badRequest().build();
        }

        newAnswer.setQuestion(questionService.getQuestionById(answerDTO.getQuestionId()));
        newAnswer.setContent(answerDTO.getContent());
        newAnswer.setCreatedAt(convertToDate(LocalDateTime.now()));

        answerService.save(newAnswer);
        return ResponseEntity.ok(newAnswer);
    }

    // Update answer
    @PutMapping("/{answerId}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable int answerId, @RequestBody AnswerDTO answerDTO) {
        if (answerDTO == null || answerId <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Answer answerOld = answerService.getAnswerByAnswerId(answerId);
        if (answerOld != null) {
            answerOld.setContent((answerDTO.getContent() != null && answerDTO.getContent().length() > 0) ? answerDTO.getContent() : answerOld.getContent());
            answerOld.setUpdatedAt(convertToDate(LocalDateTime.now()));
            answerService.save(answerOld);
            return ResponseEntity.ok(answerOld);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete answer
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Answer> deleteAnswer(@PathVariable int answerId) {
        Answer answerOld = answerService.getAnswerByAnswerId(answerId);
        if (answerOld != null) {
            answerService.delete(answerOld);
            return ResponseEntity.ok(answerOld);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Helper method to get time elapsed
    private String getTimeElapsed(LocalDateTime createdDate) {
        if (createdDate == null) {
            return "";
        }
        Duration duration = Duration.between(createdDate, LocalDateTime.now());

        if (duration.toDays() >= 1) {
            return duration.toDays() + " day(s) ago";
        } else if (duration.toHours() >= 1) {
            return duration.toHours() + " hour(s) ago";
        } else if (duration.toMinutes() >= 1) {
            return duration.toMinutes() + " minute(s) ago";
        } else {
            return duration.toSeconds() + " second(s) ago";
        }
    }

    // Hàm chuyển đổi từ Date sang LocalDateTime
    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}

