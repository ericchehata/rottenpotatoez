package rottenpotatoez.model;

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
    @Id
    private UUID id;
    private String title;
    private int rating;
    private String description;
    @ManyToOne
    private User user;
    @ManyToOne
    private Movie movie;
}
