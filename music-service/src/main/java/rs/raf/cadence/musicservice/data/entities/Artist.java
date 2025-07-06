package rs.raf.cadence.musicservice.data.entities;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "artists")
@Data
@NoArgsConstructor
public class Artist {
    @Id
    private String id;
    @Indexed(unique = true)
    @CsvBindByPosition(position = 0)
    private Long idArtist;
    @CsvBindByPosition(position = 1)
    private String strArtist;
    @CsvBindByPosition(position = 2)
    private String strArtistStripped;
    @CsvBindByPosition(position = 3)
    private String strArtistAlternate;
    @CsvBindByPosition(position = 4)
    private String strLabel;
    @CsvBindByPosition(position = 5)
    private Long idLabel;
    @CsvBindByPosition(position = 6)
    private Long intFormedYear;
    @CsvBindByPosition(position = 7)
    private Long intBornYear;
    @CsvBindByPosition(position = 8)
    private Long intDiedYear;
    @CsvBindByPosition(position = 9)
    private String strDisbanded;
    @CsvBindByPosition(position = 10)
    private String strStyle;
    @CsvBindByPosition(position = 11)
    private String strGenre;
    @CsvBindByPosition(position = 12)
    private String strMood;
    @CsvBindByPosition(position = 13)
    private String strWebsite;
    @CsvBindByPosition(position = 14)
    private String strFacebook;
    @CsvBindByPosition(position = 15)
    private String strTwitter;
    @CsvBindByPosition(position = 16)
    private String strBiographyEN;
    @CsvBindByPosition(position = 31)
    private String strGender;
    @CsvBindByPosition(position = 32)
    private Long intMembers;
    @CsvBindByPosition(position = 33)
    private String strCountry;
    @CsvBindByPosition(position = 34)
    private String strCountryCode;
    @CsvBindByPosition(position = 35)
    private String strArtistThumb;
    @CsvBindByPosition(position = 36)
    private String strArtistLogo;
    @CsvBindByPosition(position = 37)
    private String strArtistCutout;
    @CsvBindByPosition(position = 38)
    private String strArtistClearart;
    @CsvBindByPosition(position = 39)
    private String strArtistWideThumb;
    @CsvBindByPosition(position = 40)
    private String strArtistFanart;
    @CsvBindByPosition(position = 41)
    private String strArtistFanart2;
    @CsvBindByPosition(position = 42)
    private String strArtistFanart3;
    @CsvBindByPosition(position = 43)
    private String strArtistFanart4;
    @CsvBindByPosition(position = 44)
    private String strArtistBanner;
    @CsvBindByPosition(position = 45)
    private String strMusicBrainzID;
    @CsvBindByPosition(position = 46)
    private String strISNIcode;
    @CsvBindByPosition(position = 47)
    private String strLastFMChart;
}
