package rs.raf.cadence.notificationservice.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotification {
    private String to;
    private String subject;
    private String content;
}
