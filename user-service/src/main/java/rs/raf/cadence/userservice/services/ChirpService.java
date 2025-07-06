package rs.raf.cadence.userservice.services;

import rs.raf.cadence.userservice.data.dtos.RequestCreateChirpDto;
import rs.raf.cadence.userservice.data.dtos.RequestUpdateChirpDto;
import rs.raf.cadence.userservice.data.dtos.ResponseChirpDto;

import java.util.List;

public interface ChirpService {
    List<ResponseChirpDto> findChirpsByUserId(Long userId);

    ResponseChirpDto findChirpById(Long id);

    ResponseChirpDto createChirp(RequestCreateChirpDto dto);

    ResponseChirpDto updateChirp(RequestUpdateChirpDto dto);

    ResponseChirpDto likeChirp(Long id);

    ResponseChirpDto unlikeChirp(Long id);

    boolean deleteChirpById(Long id);
}
