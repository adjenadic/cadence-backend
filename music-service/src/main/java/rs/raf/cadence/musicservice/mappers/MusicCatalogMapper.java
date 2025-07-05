package rs.raf.cadence.musicservice.mappers;

import org.springframework.stereotype.Component;
import rs.raf.cadence.musicservice.data.dtos.AlbumSummaryDto;
import rs.raf.cadence.musicservice.data.dtos.ArtistSummaryDto;
import rs.raf.cadence.musicservice.data.entities.Album;
import rs.raf.cadence.musicservice.data.entities.Artist;

@Component
public class MusicCatalogMapper {
    public ArtistSummaryDto artistToArtistSummaryDto(Artist artist) {
        return new ArtistSummaryDto(
                artist.getId(),
                artist.getIdArtist(),
                artist.getStrArtist(),
                artist.getStrGenre(),
                artist.getStrStyle(),
                artist.getStrCountry(),
                artist.getStrArtistThumb(),
                artist.getIntFormedYear(),
                artist.getStrBiographyEN(),
                artist.getStrWebsite(),
                artist.getStrFacebook(),
                artist.getStrTwitter()
        );
    }

    public AlbumSummaryDto albumToAlbumSummaryDto(Album album) {
        return new AlbumSummaryDto(
                album.getId(),
                album.getIdAlbum(),
                album.getStrAlbum(),
                album.getStrArtist(),
                album.getIdArtist(),
                album.getStrGenre(),
                album.getStrStyle(),
                album.getStrLabel(),
                album.getIntYearReleased(),
                album.getStrAlbumThumb(),
                album.getIntScore(),
                album.getStrDescriptionEN()
        );
    }
}
