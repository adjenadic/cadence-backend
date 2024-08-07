package rs.raf.cadence.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.raf.cadence.userservice.data.dtos.*;
import rs.raf.cadence.userservice.services.UserService;

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

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody RequestCreateUserDto requestCreateUserDto) {
        ResponseUserDto responseUserDto = userService.createUser(requestCreateUserDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/update/email")
    public ResponseEntity<?> updateEmail(@RequestBody RequestUpdateEmailDto requestUpdateEmailDto) {
        ResponseUserDto responseUserDto = userService.updateEmail(requestUpdateEmailDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/update/username")
    public ResponseEntity<?> updateUsername(@RequestBody RequestUpdateUsernameDto requestUpdateUsernameDto) {
        ResponseUserDto responseUserDto = userService.updateUsername(requestUpdateUsernameDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/update/password")
    public ResponseEntity<?> updatePassword(@RequestBody RequestUpdatePasswordDto requestUpdatePasswordDto) {
        ResponseUserDto responseUserDto = userService.updatePassword(requestUpdatePasswordDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/update/pronouns")
    public ResponseEntity<?> updatePronouns(@RequestBody RequestUpdatePronounsDto requestUpdatePronounsDtoDto) {
        ResponseUserDto responseUserDto = userService.updatePronouns(requestUpdatePronounsDtoDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/update/about-me")
    public ResponseEntity<?> updateAboutMe(@RequestBody RequestUpdateAboutMeDto requestUpdateAboutMeDto) {
        ResponseUserDto responseUserDto = userService.updateAboutMe(requestUpdateAboutMeDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/update/password")
    public ResponseEntity<?> updateProfilePicture(@RequestBody RequestUpdateProfilePictureDto requestUpdateProfilePictureDto) {
        ResponseUserDto responseUserDto = userService.updateProfilePicture(requestUpdateProfilePictureDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @PutMapping(value = "/update/permissions")
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    public ResponseEntity<?> updatePermissions(@RequestBody RequestUpdatePermissionsDto requestUpdatePermissionsDto) {
        ResponseUserDto responseUserDto = userService.updatePermissions(requestUpdatePermissionsDto);
        return ResponseEntity.ok(responseUserDto);
    }

    @DeleteMapping(value = "/delete/id/{id}", produces = MediaType.ALL_VALUE)
    @Transactional
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUserById(id);
        return ResponseEntity.ok(isDeleted);
    }

    @DeleteMapping(value = "/delete/email/{email}", produces = MediaType.ALL_VALUE)
    @Transactional
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        boolean isDeleted = userService.deleteUserByEmail(email);
        return ResponseEntity.ok(isDeleted);
    }
}
