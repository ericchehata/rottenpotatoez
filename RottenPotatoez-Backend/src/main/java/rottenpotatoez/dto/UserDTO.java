package rottenpotatoez.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String picture;
    private boolean isAdmin;
}
