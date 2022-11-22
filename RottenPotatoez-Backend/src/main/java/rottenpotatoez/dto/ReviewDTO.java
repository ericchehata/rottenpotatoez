package rottenpotatoez.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ReviewDTO {
    private UUID id;
    private String title;
    private String description;
    private int rating;
    private UserDTO user;
    private MovieDTO movie;
}
