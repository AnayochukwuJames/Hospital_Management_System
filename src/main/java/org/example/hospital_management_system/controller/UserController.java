package org.example.hospital_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hospital_management_system.dto.Response;
import org.example.hospital_management_system.dto.UpdateUserDTO;
import org.example.hospital_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO userDTO){
        return userService.updateUser(id, userDTO);
    }

    @GetMapping("all")
    public ResponseEntity<Response> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("email")
    public ResponseEntity<Response> getUserByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }
}
