package rs.raf.cadence.userservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import rs.raf.cadence.userservice.data.dtos.RequestLoginDto;
import rs.raf.cadence.userservice.data.dtos.TokenDto;
import rs.raf.cadence.userservice.services.UserService;
import rs.raf.cadence.userservice.utils.JwtUtil;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationProvider authenticationProvider, UserService userService, JwtUtil jwtUtil) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto) {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(requestLoginDto.getEmail(), requestLoginDto.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(new TokenDto(jwtUtil.generateToken(userService.findUserByEmail(requestLoginDto.getEmail()))));
    }
}
