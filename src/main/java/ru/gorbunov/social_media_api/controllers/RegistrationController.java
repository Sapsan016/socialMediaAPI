package ru.gorbunov.social_media_api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gorbunov.social_media_api.dto.JwtRequest;
import ru.gorbunov.social_media_api.models.User;

import java.util.Collections;
@RestController
@RequestMapping("/registration")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class RegistrationController {
   // BCryptPasswordEncoder passwordEncoder;

//    @PostMapping()
//    public ResponseEntity<?> addUser(@RequestBody JwtRequest reqRequest) {
//        User user = new User();
//        user.setUsername(reqRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(reqRequest.getPassword()));
//        user.setEmail(reqRequest.getEmail());
//        user.setRoles(Collections.singleton(roleRepository.getReferenceById(1L)));
//
//
//        return ResponseEntity.ok(userService.addNewUser(user));
//
//    }
}
