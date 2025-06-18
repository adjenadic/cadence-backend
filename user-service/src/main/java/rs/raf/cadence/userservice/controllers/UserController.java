package rs.raf.cadence.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.raf.cadence.userservice.data.dtos.*;
import rs.raf.cadence.userservice.exceptions.*;
import rs.raf.cadence.userservice.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @PostMapping(value = "/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String verificationToken) {
        try {
            boolean verified = userService.verifyEmail(verificationToken);
            return ResponseEntity.ok(verified);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (VerificationCodeNotFoundException | InvalidVerificationCodeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (VerificationCodeExpiredException e) {
            return ResponseEntity.status(410).body(e.getMessage());
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody RequestCreateUserDto requestCreateUserDto) {
        try {
            ResponseUserDto responseUserDto = userService.createUser(requestCreateUserDto);
            return ResponseEntity.ok(responseUserDto);
        } catch (EmailAlreadyTakenException | UsernameAlreadyTakenException | UnmatchedPasswordException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Registration failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping(value = "/email")
    public ResponseEntity<?> updateEmail(@RequestBody RequestUpdateEmailDto requestUpdateEmailDto) {
        ResponseUserDto responseUserDto = userService.updateEmail(requestUpdateEmailDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/username")
    public ResponseEntity<?> updateUsername(@RequestBody RequestUpdateUsernameDto requestUpdateUsernameDto) {
        ResponseUserDto responseUserDto = userService.updateUsername(requestUpdateUsernameDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/password")
    public ResponseEntity<?> updatePassword(@RequestBody RequestUpdatePasswordDto requestUpdatePasswordDto) {
        try {
            ResponseUserDto responseUserDto = userService.updatePassword(requestUpdatePasswordDto);
            return ResponseEntity.ok(responseUserDto);
        } catch (EmailNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping(value = "/pronouns")
    public ResponseEntity<?> updatePronouns(@RequestBody RequestUpdatePronounsDto requestUpdatePronounsDtoDto) {
        ResponseUserDto responseUserDto = userService.updatePronouns(requestUpdatePronounsDtoDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/about-me")
    public ResponseEntity<?> updateAboutMe(@RequestBody RequestUpdateAboutMeDto requestUpdateAboutMeDto) {
        ResponseUserDto responseUserDto = userService.updateAboutMe(requestUpdateAboutMeDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/profile-picture")
    public ResponseEntity<?> updateProfilePicture(@RequestBody RequestUpdateProfilePictureDto requestUpdateProfilePictureDto) {
        ResponseUserDto responseUserDto = userService.updateProfilePicture(requestUpdateProfilePictureDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/permissions")
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    public ResponseEntity<?> updatePermissions(@RequestBody RequestUpdatePermissionsDto requestUpdatePermissionsDto) {
        ResponseUserDto responseUserDto = userService.updatePermissions(requestUpdatePermissionsDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    @Transactional
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUserById(id);
        return ResponseEntity.ok(isDeleted);
    }

    @DeleteMapping(value = "/{email}", produces = MediaType.ALL_VALUE)
    @Transactional
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        boolean isDeleted = userService.deleteUserByEmail(email);
        return ResponseEntity.ok(isDeleted);
    }
}
