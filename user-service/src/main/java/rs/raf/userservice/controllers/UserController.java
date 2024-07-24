package rs.raf.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.raf.userservice.data.dtos.CreateRequestUserDto;
import rs.raf.userservice.data.dtos.ResponseUserDto;
import rs.raf.userservice.services.UserService;
import rs.raf.userservice.utils.JwtUtil;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        ResponseUserDto responseUserDto = userService.findUserById(id);
        if (responseUserDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: id " + id);
        }
        return ResponseEntity.ok(responseUserDto);
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserByEmail(@PathVariable String email) {
        ResponseUserDto responseUserDto = userService.findUserByEmail(email);
        if (responseUserDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: email " + email);
        }
        return ResponseEntity.ok(responseUserDto);
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
        ResponseUserDto responseUserDto = userService.findUserByUsername(username);
        if (responseUserDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: username " + username);
        }
        return ResponseEntity.ok(responseUserDto);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody CreateRequestUserDto createRequestUserDto) {
        try {
            ResponseUserDto responseUserDto = userService.createUser(createRequestUserDto);
            return ResponseEntity.ok(responseUserDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/id/{id}", produces = MediaType.ALL_VALUE)
    @Transactional
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            boolean isUserDeleted = userService.deleteUserById(id);
            if (isUserDeleted) {
                return ResponseEntity.ok("User deleted successfully: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: id " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/email/{email}", produces = MediaType.ALL_VALUE)
    @Transactional
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        try {
            boolean isUserDeleted = userService.deleteUserByEmail(email);
            if (isUserDeleted) {
                return ResponseEntity.ok("User deleted successfully: " + email);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: email " + email);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
