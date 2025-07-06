package rs.raf.cadence.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import rs.raf.cadence.userservice.data.dtos.RequestCreateChirpDto;
import rs.raf.cadence.userservice.data.dtos.RequestUpdateChirpDto;
import rs.raf.cadence.userservice.data.dtos.ResponseChirpDto;
import rs.raf.cadence.userservice.data.entities.Chirp;
import rs.raf.cadence.userservice.data.entities.User;
import rs.raf.cadence.userservice.exceptions.ChirpNotFoundException;
import rs.raf.cadence.userservice.exceptions.IdNotFoundException;
import rs.raf.cadence.userservice.mappers.ChirpMapper;
import rs.raf.cadence.userservice.repositories.ChirpRepository;
import rs.raf.cadence.userservice.repositories.UserRepository;
import rs.raf.cadence.userservice.services.ChirpService;
import rs.raf.cadence.userservice.utils.SpringSecurityUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChirpServiceImpl implements ChirpService {
    private final UserRepository userRepository;
    private final ChirpRepository chirpRepository;
    private final ChirpMapper chirpMapper;

    public List<ResponseChirpDto> findChirpsByUserId(Long userId) {
        return chirpRepository.findChirpsByUserIdOrderByTimestampDesc(userId).stream()
                .map(chirpMapper::chirpToResponseChirpDto)
                .collect(Collectors.toList());
    }

    public ResponseChirpDto findChirpById(Long id) {
        Chirp chirp = chirpRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        return chirpMapper.chirpToResponseChirpDto(chirp);
    }

    public ResponseChirpDto createChirp(RequestCreateChirpDto requestCreateChirpDto) {
        User user = userRepository.findById(requestCreateChirpDto.getUserId()).orElseThrow(() -> new IdNotFoundException(requestCreateChirpDto.getUserId()));
        User chirper = userRepository.findById(requestCreateChirpDto.getChirperId()).orElseThrow(() -> new IdNotFoundException(requestCreateChirpDto.getChirperId()));
        Chirp chirp = chirpMapper.requestCreateChirpDtoToChirp(requestCreateChirpDto, user, chirper);
        chirpRepository.save(chirp);
        return chirpMapper.chirpToResponseChirpDto(chirp);
    }

    public ResponseChirpDto updateChirp(RequestUpdateChirpDto requestUpdateChirpDto) {
        Optional<Chirp> chirp = chirpRepository.findById(requestUpdateChirpDto.getChirpId());

        if (chirp.isPresent()) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(chirp.get().getChirper().getEmail())) {
                Chirp updatedChirp = chirp.get();

                updatedChirp.setContent(requestUpdateChirpDto.getContent());
                updatedChirp.setTimestamp(System.currentTimeMillis());

                chirpRepository.save(updatedChirp);
                return chirpMapper.chirpToResponseChirpDto(updatedChirp);
            } else {
                throw new AccessDeniedException("You do not have permission to update this chirp.");
            }
        } else {
            throw new ChirpNotFoundException(requestUpdateChirpDto.getChirpId());
        }
    }

    public ResponseChirpDto likeChirp(Long id) {
        Chirp chirp = chirpRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        chirp.setLikes(chirp.getLikes() + 1);
        chirpRepository.save(chirp);
        return chirpMapper.chirpToResponseChirpDto(chirp);
    }

    public ResponseChirpDto unlikeChirp(Long id) {
        Chirp chirp = chirpRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        chirp.setLikes(chirp.getLikes() - 1);
        chirpRepository.save(chirp);
        return chirpMapper.chirpToResponseChirpDto(chirp);
    }

    public boolean deleteChirpById(Long id) {
        Optional<Chirp> chirp = chirpRepository.findById(id);
        if (chirp.isPresent()) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(chirp.get().getChirper().getEmail()) ||
                    SpringSecurityUtil.hasPermission("DELETE_CHIRPS")) {
                chirpRepository.deleteById(id);
                return true;
            } else {
                throw new AccessDeniedException("You do not have permission to delete this chirp.");
            }
        } else {
            throw new IdNotFoundException(id);
        }
    }
}
