package com.lria62.QuestionForum.Controller;

import com.lria62.QuestionForum.Model.Role;
import com.lria62.QuestionForum.Service.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.lria62.QuestionForum.Model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    @RequestMapping(value = "/")
    public ResponseEntity<String> redirect() {
        return ResponseEntity.ok("Welcome to User API");
    }


    @GetMapping
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> getUserByLogin(@RequestParam String username, @RequestParam String password) {
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Username or password is empty!");
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body("Wrong password!");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/insertUser")
    public ResponseEntity<?> insertUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.notFound().build();
        }

        user.setRole(new Role(2, "User"));
        user.setAvatar("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgI3iX7tf1HiAnbVKSpyns2b0moiUKSJE2uQ&s");
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@ModelAttribute UserUpdateDto userUpdateDto) throws IOException {
        User user = userService.findByUsername(userUpdateDto.getUsername());
        if (user ==  null) {
            return ResponseEntity.notFound().build();
        }

        // Cập nhật các trường không null
        if(userUpdateDto.getEmail() != null && userUpdateDto.getEmail().length() >0){

            if(user.getEmail() != userUpdateDto.getEmail()){
                if (userService.findByEmail(userUpdateDto.getEmail()) == null) {
                    user.setEmail(userUpdateDto.getEmail());
                } else {
                    return ResponseEntity.badRequest().body("already have that email in database!");
                }
            }


        }
        user.setGender(userUpdateDto.getGender() != null ? userUpdateDto.getGender() : user.getGender());
        user.setPassword((userUpdateDto.getPassword() != null && userUpdateDto.getPassword().length() > 0) ? userUpdateDto.getPassword() : user.getPassword());

        if (userUpdateDto.getAvatar() != null) {
            MultipartFile avatar = userUpdateDto.getAvatar();
            var uploadResult = cloudinary.uploader().upload(avatar.getBytes(),
                    ObjectUtils.asMap("public_id", avatar.getOriginalFilename()));
            user.setAvatar(uploadResult.get("secure_url").toString());
        }

        userService.save(user);
        return ResponseEntity.ok(user);
    }
    @Data
    public class UserUpdateDto {
        private String username;
        private String password;
        private String email;
        private Boolean gender;
        private MultipartFile avatar;
    }
}

