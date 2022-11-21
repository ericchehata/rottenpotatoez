package rottenpotatoez.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import rottenpotatoez.model.Rating;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MovieDTO {
    private UUID id;
    private String title;
    private String description;
    private int duration;
    private LocalDate releaseDate;
    private String picture;
    private String rating;
    private List<String> genres;
}
