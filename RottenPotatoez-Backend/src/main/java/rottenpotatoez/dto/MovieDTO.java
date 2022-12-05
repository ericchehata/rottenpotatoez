package rottenpotatoez.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MovieDTO {
    private UUID id;
    private String title;
    private int duration;
    private LocalDate releaseDate;
    private String picture;
    private String rating;
    private List<String> genres;
}
