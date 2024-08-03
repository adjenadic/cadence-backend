package rs.raf.cadence.musicservice.data.entities;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "albums")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    private String id;
    private Long idAlbum;
    private Long idArtist;
    private Long idLabel;
    private String strAlbum;
    private String strAlbumStripped;
    private String strArtist;
    private String strArtistStripped;
    private Long intYearReleased;
    private String strStyle;
    private String strGenre;
    private String strLabel;
    private String strReleaseFormat;
    private Long intSales;
    private String strAlbumThumb;
    private String strAlbumThumbHQ;
    private String strAlbumThumbBack;
    private String strAlbumCDart;
    private String strAlbumSpine;
    private String strAlbum3DCase;
    private String strAlbum3DFlat;
    private String strAlbum3DFace;
    private String strAlbum3DThumb;
    private String strDescriptionEN;
    private String strDescriptionDE;
    private String strDescriptionFR;
    private String strDescriptionCN;
    private String strDescriptionIT;
    private String strDescriptionJP;
    private String strDescriptionRU;
    private String strDescriptionES;
    private String strDescriptionPT;
    private String strDescriptionSE;
    private String strDescriptionNL;
    private String strDescriptionHU;
    private String strDescriptionNO;
    private String strDescriptionIL;
    private String strDescriptionPL;
    private Long intLoved;
    private Long intScore;
    private Long intScoreVotes;
    private String strReview;
    private String strMood;
    private String strTheme;
    private String strSpeed;
    private String strLocation;
    private String strMusicBrainzID;
    private String strMusicBrainzArtistID;
    private String strAllMusicID;
    private String strBBCReviewID;
    private String strRateYourMusicID;
    private String strDiscogsID;
    private String strWikidataID;
    private String strWikipediaID;
    private String strGeniusID;
    private String strLyricWikiID;
    private String strMusicMozID;
    private String strItunesID;
    private String strAmazonID;
    private String strLocked;
}
