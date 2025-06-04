package rs.raf.cadence.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import rs.raf.cadence.userservice.data.dtos.RequestLoginDto;
import rs.raf.cadence.userservice.data.dtos.TokenDto;
import rs.raf.cadence.userservice.data.entities.User;
import rs.raf.cadence.userservice.exceptions.EmailNotVerifiedException;
import rs.raf.cadence.userservice.repositories.UserRepository;
import rs.raf.cadence.userservice.services.UserService;
import rs.raf.cadence.userservice.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto) {
        try {
            User user = userRepository.findUserByEmail(requestLoginDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User with email " + requestLoginDto.getEmail() + " not found."));

            if (!user.isEmailVerified()) {
                throw new EmailNotVerifiedException(user.getEmail());
            }

            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(requestLoginDto.getEmail(), requestLoginDto.getPassword()));
        } catch (EmailNotVerifiedException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Please verify your email address before logging in.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Login failed.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        return ResponseEntity.ok(new TokenDto(jwtUtil.generateToken(userService.findUserByEmail(requestLoginDto.getEmail()))));
    }
}
