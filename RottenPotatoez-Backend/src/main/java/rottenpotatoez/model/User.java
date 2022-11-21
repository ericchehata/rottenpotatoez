package rottenpotatoez.model;

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
    @Id
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String picture;
    private boolean isAdmin;
}
