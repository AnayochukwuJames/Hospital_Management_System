package org.example.hospital_management_system.service;

import lombok.RequiredArgsConstructor;
import org.example.hospital_management_system.dto.Response;
import org.example.hospital_management_system.dto.UpdateUserDTO;
import org.example.hospital_management_system.entity.User;
import org.example.hospital_management_system.mapper.Mapper;
import org.example.hospital_management_system.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper = Mapper.mapper;

    public ResponseEntity<Response> updateUser(Long id, UpdateUserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            Response response = new Response(false, "Invalid user.", null);
            return ResponseEntity.status(404).body(response);
        }
        User existingUser = optionalUser.get();
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setMiddleName(userDTO.getMiddleName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setContactAddress(userDTO.getContactAddress());

        User updatedUser = userRepository.save(existingUser);

        UpdateUserDTO responseUserDTO = mapper.mapToUserDto(updatedUser);
        Map<String, Object> map = new HashMap<>();
        map.put("user", responseUserDTO);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<Response> getAllUsers() {
        List<User> user = userRepository.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("users", user);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            Response response = new Response(false, "User not found.", null);
            return ResponseEntity.status(404).body(response);
        }
        User user = optionalUser.get();
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username);
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            Response response = new Response(true, "success", map);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException exception) {
            System.out.println(exception.getMessage());
            Response response = new Response(false, "User not found.", null);
            return ResponseEntity.status(404).body(response);
        }
    }
}
