package rs.raf.cadence.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.raf.cadence.userservice.data.dtos.RequestCreateChirpDto;
import rs.raf.cadence.userservice.data.dtos.RequestUpdateChirpDto;
import rs.raf.cadence.userservice.data.dtos.ResponseChirpDto;
import rs.raf.cadence.userservice.services.ChirpService;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/api/chirps")
public class ChirpController {
    private final ChirpService chirpService;

    @GetMapping(value = "/user-id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findChirpsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(chirpService.findChirpsByUserId(userId));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findChirpById(@PathVariable Long id) {
        return ResponseEntity.ok(chirpService.findChirpById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createChirp(@RequestBody RequestCreateChirpDto requestCreateChirpDto) {
        ResponseChirpDto responseChirpDto = chirpService.createChirp(requestCreateChirpDto);
        return ResponseEntity.ok(responseChirpDto);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateChirp(@RequestBody RequestUpdateChirpDto requestUpdateChirpDto) {
        ResponseChirpDto responseChirpDto = chirpService.updateChirp(requestUpdateChirpDto);
        return ResponseEntity.ok(responseChirpDto);
    }

    @PutMapping(value = "/like/{id}")
    public ResponseEntity<?> likeChirp(@PathVariable Long id) {
        ResponseChirpDto responseChirpDto = chirpService.likeChirp(id);
        return ResponseEntity.ok(responseChirpDto);
    }

    @PutMapping(value = "/unlike/{id}")
    public ResponseEntity<?> unlikeChirp(@PathVariable Long id) {
        ResponseChirpDto responseChirpDto = chirpService.unlikeChirp(id);
        return ResponseEntity.ok(responseChirpDto);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.ALL_VALUE)
    @Transactional
    public ResponseEntity<?> deleteChirpById(@PathVariable Long id) {
        boolean isDeleted = chirpService.deleteChirpById(id);
        return ResponseEntity.ok(isDeleted);
    }
}
