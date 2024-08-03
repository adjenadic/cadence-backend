package rs.raf.cadence.userservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLoginDto {
    private String email;
    private String password;
}
