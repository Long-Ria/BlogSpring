package com.lria62.QuestionForum.Controller;



import com.lria62.QuestionForum.DTO.QuestionDTO;
import com.lria62.QuestionForum.Model.Question;
import com.lria62.QuestionForum.Model.Answer;

import com.lria62.QuestionForum.Model.User;
import com.lria62.QuestionForum.Service.AnswerService;
import com.lria62.QuestionForum.Service.QuestionService;
import com.lria62.QuestionForum.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
public class QuestionRestController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllQuestions(
            @RequestParam(required = false) String questionContent,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String createdTime,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "3") int pageSize) {

        // Lấy danh sách tất cả câu hỏi
        List<Question> questions = questionService.getAllQuestions();


        if (questionContent != null && !questionContent.trim().isEmpty()) {
            questions = questions.stream()
                    .filter(q -> q.getContent().toLowerCase().contains(questionContent.toLowerCase()) ||
                            q.getTitle().toLowerCase().contains(questionContent.toLowerCase()) ||
                            q.getUser().getUsername().toLowerCase().contains(questionContent.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (createdTime != null && !createdTime.trim().isEmpty()) {
            LocalDateTime currentTime = LocalDateTime.now();

            questions = questions.stream()
                    .filter(q -> (createdTime.contains("1") && convertToLocalDateTime(q.getCreatedDate()).isAfter(currentTime.minus(1, ChronoUnit.DAYS))) ||
                            (createdTime.contains("7") && convertToLocalDateTime(q.getCreatedDate()).isAfter(currentTime.minus(7, ChronoUnit.DAYS))) ||
                            (createdTime.contains("30") && convertToLocalDateTime(q.getCreatedDate()).isAfter(currentTime.minus(30, ChronoUnit.DAYS))))
                    .collect(Collectors.toList());
        }

        // Sắp xếp
        if ("newest".equals(sort)) {
            questions.sort(Comparator.comparing(Question::getCreatedDate).reversed());
        } else if ("oldest".equals(sort)) {
            questions.sort(Comparator.comparing(Question::getCreatedDate));
        }

        // Phân trang
        int totalItems = questions.size();
        List<Question> pagedQuestions = questions.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        List<Map<String, Object>> result = pagedQuestions.stream()
                .map(q -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("questionId", q.getQuestionId());
                    map.put("avatar", q.getUser().getAvatar());
                    map.put("title", q.getTitle());
                    map.put("content", q.getContent());
                    map.put("userId", q.getUser().getUserId());
                    map.put("author", q.getUser().getUsername());
                    map.put("createdDate", getTimeElapsed(convertToLocalDateTime(q.getCreatedDate())));
                    map.put("totalAnswer", q.getAnswers().size());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "totalItems", totalItems,
                "page", pageNumber,
                "pageSize", pageSize,
                "questions", result
        ));
    }


    // Hàm chuyển đổi từ Date sang LocalDateTime
    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable int id) {
        Question question = questionService.getQuestionById(id);

        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        // Sử dụng HashMap thay vì Map.of
        Map<String, Object> result = new HashMap<>();
        result.put("questionId", question.getQuestionId());
        result.put("title", question.getTitle());
        result.put("content", question.getContent());
        result.put("image", question.getUser().getAvatar());
        result.put("userId", question.getUser().getUserId());
        result.put("user", question.getUser().getUsername());
        result.put("createdAt", getTimeElapsed(convertToLocalDateTime(question.getCreatedDate())));
        result.put("status", question.getStatus());

        // Xử lý answers
        List<Map<String, Object>> answers = question.getAnswers().stream()
                .sorted(Comparator.comparing(Answer::getCreatedAt).reversed())  // Lấy CreatedDate từ Answer
                .map(a -> {
                    Map<String, Object> answerMap = new HashMap<>();
                    answerMap.put("answerId", a.getAnswerId());
                    answerMap.put("userId", a.getUser().getUserId());
                    answerMap.put("user", a.getUser().getUsername());
                    answerMap.put("image", a.getUser().getAvatar());
                    answerMap.put("createdAt", getTimeElapsed(convertToLocalDateTime(a.getCreatedAt())));
                    answerMap.put("content", a.getContent());
                    answerMap.put("updated", a.getUpdatedAt() != null ? a.getUpdatedAt().toString() : null);
                    return answerMap;
                })
                .collect(Collectors.toList());

        result.put("answers", answers);

        return ResponseEntity.ok(result);
    }



    @PostMapping
    public ResponseEntity<?> insertQuestion(@Validated @RequestBody QuestionDTO questionDTO) {
        // Tạo đối tượng Question từ DTO
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());

        // Lấy đối tượng User từ userId
        User user = userService.getUserById(questionDTO.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
        question.setUser(user);

        question.setCreatedDate(convertToDate(LocalDateTime.now()));
        question.setStatus(questionDTO.isStatus());

        // Lưu câu hỏi vào cơ sở dữ liệu
        questionService.save(question);
        return ResponseEntity.ok(question);
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable int id, @Validated @RequestBody QuestionDTO questionDTO) {
        Question question = questionService.getQuestionById(id);

        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        question.setTitle(questionDTO.getTitle() != null ? questionDTO.getTitle() : question.getTitle());
        question.setContent(questionDTO.getContent() != null ? questionDTO.getContent() : question.getContent());
        question.setStatus(questionDTO.isStatus() != null ? questionDTO.isStatus() : question.getStatus());

        questionService.save(question);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable int id) {
        // Lấy Question theo id
        Question question = questionService.getQuestionById(id);

        // Kiểm tra nếu không tìm thấy Question
        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        // Nếu Question có Answer, xóa tất cả Answer liên quan
        if (question.getAnswers() != null && !question.getAnswers().isEmpty()) {
            // Xóa Answer trước khi xóa Question
            answerService.deleteAll(question.getAnswers());
        }

        // Sau khi xóa các Answer, xóa Question
        questionService.delete(question);

        return ResponseEntity.ok(question);
    }


    private String getTimeElapsed(LocalDateTime createdDate) {
        if (createdDate == null) {
            return "";
        }

        long seconds = ChronoUnit.SECONDS.between(createdDate, LocalDateTime.now());
        long minutes = ChronoUnit.MINUTES.between(createdDate, LocalDateTime.now());
        long hours = ChronoUnit.HOURS.between(createdDate, LocalDateTime.now());
        long days = ChronoUnit.DAYS.between(createdDate, LocalDateTime.now());

        if (days > 0) {
            return days + " day(s) ago";
        } else if (hours > 0) {
            return hours + " hour(s) ago";
        } else if (minutes > 0) {
            return minutes + " minute(s) ago";
        } else {
            return seconds + " second(s) ago";
        }
    }
}
