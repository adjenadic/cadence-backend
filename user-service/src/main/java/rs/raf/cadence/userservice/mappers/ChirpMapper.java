package rs.raf.cadence.userservice.mappers;

import org.springframework.stereotype.Component;
import rs.raf.cadence.userservice.data.dtos.RequestCreateChirpDto;
import rs.raf.cadence.userservice.data.dtos.ResponseChirpDto;
import rs.raf.cadence.userservice.data.entities.Chirp;
import rs.raf.cadence.userservice.data.entities.User;

@Component
public class ChirpMapper {
    public Chirp requestCreateChirpDtoToChirp(RequestCreateChirpDto dto, User user, User chirper) {
        return new Chirp(
                dto.getContent(),
                user,
                chirper
        );
    }

    public ResponseChirpDto chirpToResponseChirpDto(Chirp chirp) {
        return new ResponseChirpDto(
                chirp.getId(),
                chirp.getContent(),
                chirp.getTimestamp(),
                chirp.getUser().getId(),
                chirp.getChirper().getId(),
                chirp.getChirper().getUsername(),
                chirp.getChirper().getProfilePicture(),
                chirp.getLikes(),
                chirp.isEdited()
        );
    }
}
