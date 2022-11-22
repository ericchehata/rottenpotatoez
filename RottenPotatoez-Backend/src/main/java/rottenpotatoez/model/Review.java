package rottenpotatoez.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id @NotNull
    private UUID id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @Min(1) @Max(5)
    private int rating;
    @ManyToOne @NotNull
    private User user;
    @ManyToOne @NotNull
    private Movie movie;
}
