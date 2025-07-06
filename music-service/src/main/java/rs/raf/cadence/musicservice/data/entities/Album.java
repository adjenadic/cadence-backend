package rs.raf.cadence.musicservice.data.entities;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "albums")
@Data
@NoArgsConstructor
public class Album {
    @Id
    private String id;
    @Indexed(unique = true)
    @CsvBindByPosition(position = 0)
    private Long idAlbum;
    @CsvBindByPosition(position = 1)
    private Long idArtist;
    @CsvBindByPosition(position = 2)
    private Long idLabel;
    @CsvBindByPosition(position = 3)
    private String strAlbum;
    @CsvBindByPosition(position = 4)
    private String strAlbumStripped;
    @CsvBindByPosition(position = 5)
    private String strArtist;
    @CsvBindByPosition(position = 6)
    private String strArtistStripped;
    @CsvBindByPosition(position = 7)
    private Long intYearReleased;
    @CsvBindByPosition(position = 8)
    private String strStyle;
    @CsvBindByPosition(position = 9)
    private String strGenre;
    @CsvBindByPosition(position = 10)
    private String strLabel;
    @CsvBindByPosition(position = 11)
    private String strReleaseFormat;
    @CsvBindByPosition(position = 12)
    private String intSales;
    @CsvBindByPosition(position = 13)
    private String strAlbumThumb;
    @CsvBindByPosition(position = 14)
    private String strAlbumThumbHQ;
    @CsvBindByPosition(position = 15)
    private String strAlbumThumbBack;
    @CsvBindByPosition(position = 16)
    private String strAlbumCDart;
    @CsvBindByPosition(position = 17)
    private String strAlbumSpine;
    @CsvBindByPosition(position = 18)
    private String strAlbum3DCase;
    @CsvBindByPosition(position = 19)
    private String strAlbum3DFlat;
    @CsvBindByPosition(position = 20)
    private String strAlbum3DFace;
    @CsvBindByPosition(position = 21)
    private String strAlbum3DThumb;
    @CsvBindByPosition(position = 22)
    private String strDescriptionEN;
    @CsvBindByPosition(position = 37)
    private Long intLoved;
    @CsvBindByPosition(position = 38)
    private Double intScore;
    @CsvBindByPosition(position = 39)
    private Long intScoreVotes;
    @CsvBindByPosition(position = 40)
    private String strReview;
    @CsvBindByPosition(position = 41)
    private String strMood;
    @CsvBindByPosition(position = 42)
    private String strTheme;
    @CsvBindByPosition(position = 43)
    private String strSpeed;
    @CsvBindByPosition(position = 44)
    private String strLocation;
    @CsvBindByPosition(position = 45)
    private String strMusicBrainzID;
    @CsvBindByPosition(position = 46)
    private String strMusicBrainzArtistID;
    @CsvBindByPosition(position = 47)
    private String strAllMusicID;
    @CsvBindByPosition(position = 48)
    private String strBBCReviewID;
    @CsvBindByPosition(position = 49)
    private String strRateYourMusicID;
    @CsvBindByPosition(position = 50)
    private String strDiscogsID;
    @CsvBindByPosition(position = 51)
    private String strWikidataID;
    @CsvBindByPosition(position = 52)
    private String strWikipediaID;
    @CsvBindByPosition(position = 53)
    private String strGeniusID;
    @CsvBindByPosition(position = 54)
    private String strLyricWikiID;
    @CsvBindByPosition(position = 55)
    private String strMusicMozID;
    @CsvBindByPosition(position = 56)
    private String strItunesID;
    @CsvBindByPosition(position = 57)
    private String strAmazonID;
}
