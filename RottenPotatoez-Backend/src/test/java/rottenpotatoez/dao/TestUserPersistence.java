package rottenpotatoez.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.model.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestUserPersistence {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        reviewRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testReviewPersistence() {
        String username = "testUser";
        String password = "testPassword123";
        String firstName = "testFirstName";
        String lastName = "testLastName";
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        String email = "test@mail.ca";
        String userPicture = "userPicture.png";
        boolean isAdmin = false;
        User user = new User(username, password, firstName, lastName, dateOfBirth, email, userPicture, isAdmin);
        userRepository.save(user);

        user = userRepository.findByUsername(username).get();

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(dateOfBirth, user.getDateOfBirth());
        assertEquals(email, user.getEmail());
        assertEquals(userPicture, user.getPicture());
        assertEquals(isAdmin, user.isAdmin());

    }

}
