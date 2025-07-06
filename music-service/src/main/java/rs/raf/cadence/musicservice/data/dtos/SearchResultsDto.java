package rs.raf.cadence.musicservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultsDto {
    private List<ArtistSummaryDto> artists;
    private List<AlbumSummaryDto> albums;
}