package rottenpotatoez.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id @NotNull
    private UUID id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @Min(1)
    private int duration;
    @NotNull
    private LocalDate releaseDate;
    @NotBlank
    private String picture;
    @NotNull
    private Rating rating;
    @ElementCollection(targetClass=Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="movie_genres")
    @Column(name="genres")
    @NotEmpty
    private List<Genre> genres;
}
