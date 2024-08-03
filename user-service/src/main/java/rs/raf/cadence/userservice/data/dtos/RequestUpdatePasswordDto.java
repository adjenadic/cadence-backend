package rs.raf.cadence.userservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdatePasswordDto {
    private String email;
    private String currentPassword;
    private String updatedPassword;
    private String updatedPasswordConfirmation;
}
