package rs.raf.cadence.musicservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistSummaryDto {
    private String id;
    private Long idArtist;
    private String strArtist;
    private String strGenre;
    private String strStyle;
    private String strCountry;
    private String strArtistThumb;
    private Long intFormedYear;
    private String strBiographyEN;
    private String strWebsite;
    private String strFacebook;
    private String strTwitter;
}
