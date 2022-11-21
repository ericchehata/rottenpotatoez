package rottenpotatoez.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private UUID id;
    private String title;
    private String description;
    private int duration;
    private LocalDate releaseDate;
    private String picture;
    private Rating rating;
    @ElementCollection(targetClass=Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="movie_genres")
    @Column(name="genres")
    private List<Genre> genres;
}
