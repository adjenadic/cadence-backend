package rs.raf.cadence.userservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEmailVerificationDto {
    private String email;
    private String verificationCode;
}
