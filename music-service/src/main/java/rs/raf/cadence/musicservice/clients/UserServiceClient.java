package rs.raf.cadence.musicservice.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rs.raf.cadence.musicservice.data.dtos.UserDetailsDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserServiceClient {
    private final RestTemplate restTemplate;

    @Value("${app.user-service.url:http://localhost:8000}")
    private String userServiceUrl;

    public UserServiceClient() {
        this.restTemplate = new RestTemplate();
    }

    public UserDetailsDto getUserDetails(Long userId) {
        try {
            String url = userServiceUrl + "/api/users/" + userId;
            ResponseEntity<UserDetailsDto> response = restTemplate.getForEntity(url, UserDetailsDto.class);
            return response.getBody();
        } catch (Exception e) {
            log.warn("Failed to fetch user details for userId: {}", userId, e);
            return createFallbackUserDetails(userId);
        }
    }

    public Map<Long, UserDetailsDto> getUserDetailsBatch(List<Long> userIds) {
        return userIds.stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        this::getUserDetails
                ));
    }

    private UserDetailsDto createFallbackUserDetails(Long userId) {
        UserDetailsDto fallback = new UserDetailsDto();
        fallback.setId(userId);
        fallback.setUsername("User " + userId);
        fallback.setProfilePicture(null);
        return fallback;
    }
}