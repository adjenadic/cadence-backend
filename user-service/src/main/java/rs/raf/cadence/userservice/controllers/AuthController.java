package rs.raf.cadence.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
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
            User user = userRepository.findUserByUsername(requestLoginDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User with email " + requestLoginDto.getEmail() + " not found."));

            if (!user.isEmailVerified()) {
                throw new EmailNotVerifiedException(user.getEmail());
            }

            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(requestLoginDto.getEmail(), requestLoginDto.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(new TokenDto(jwtUtil.generateToken(userService.findUserByEmail(requestLoginDto.getEmail()))));
    }
}
