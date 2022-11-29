package rottenpotatoez.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String picture;
    private boolean isAdmin;
}
