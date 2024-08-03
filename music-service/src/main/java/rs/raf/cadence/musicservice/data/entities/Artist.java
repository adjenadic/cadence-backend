package rs.raf.cadence.musicservice.data.entities;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "artists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    @Id
    private String id;
    private Long idArtist;
    private String strArtist;
    private String strArtistStripped;
    private String strArtistAlternate;
    private String strLabel;
    private Long idLabel;
    private Long intFormedYear;
    private Long intBornYear;
    private Long intDiedYear;
    private String strDisbanded;
    private String strStyle;
    private String strGenre;
    private String strMood;
    private String strWebsite;
    private String strFacebook;
    private String strTwitter;
    private String strBiographyEN;
    private String strBiographyDE;
    private String strBiographyFR;
    private String strBiographyCN;
    private String strBiographyIT;
    private String strBiographyJP;
    private String strBiographyRU;
    private String strBiographyES;
    private String strBiographyPT;
    private String strBiographySE;
    private String strBiographyNL;
    private String strBiographyHU;
    private String strBiographyNO;
    private String strBiographyIL;
    private String strBiographyPL;
    private String strGender;
    private Long intMembers;
    private String strCountry;
    private String strCountryCode;
    private String strArtistThumb;
    private String strArtistLogo;
    private String strArtistCutout;
    private String strArtistClearart;
    private String strArtistWideThumb;
    private String strArtistFanart;
    private String strArtistFanart2;
    private String strArtistFanart3;
    private String strArtistFanart4;
    private String strArtistBanner;
    private String strMusicBrainzID;
    private String strISNIcode;
    private String strLastFMChart;
    private Long intCharted;
    private String strLocked;
}
