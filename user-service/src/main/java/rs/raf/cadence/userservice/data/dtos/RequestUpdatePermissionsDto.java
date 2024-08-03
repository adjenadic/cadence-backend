package rs.raf.cadence.userservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdatePermissionsDto {
    private String email;
    private List<String> permissions;
}
