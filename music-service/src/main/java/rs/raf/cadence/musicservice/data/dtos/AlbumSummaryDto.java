package rs.raf.cadence.musicservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumSummaryDto {
    private String id;
    private Long idAlbum;
    private String strAlbum;
    private String strArtist;
    private Long idArtist;
    private String strGenre;
    private String strStyle;
    private String strLabel;
    private Long intYearReleased;
    private String strAlbumThumb;
    private Double intScore;
    private String strDescriptionEN;
}
