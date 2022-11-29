package rottenpotatoez.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import rottenpotatoez.dao.UserRepository;
import rottenpotatoez.dto.UserDTO;
import rottenpotatoez.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "Password123";
    private static final String USER_FIRST_NAME = "firstName";
    private static final String USER_LAST_NAME = "lastName";
    private static final LocalDate USER_DATE_OF_BIRTH = LocalDate.of(2000, 1, 1);
    private static final String USER_EMAIL = "email@mail.ca";
    private static final String USER_PICTURE = "picture.png";
    private static final boolean USER_ADMIN = false;

    private static final String USER2_USERNAME = "username2";
    private static final String USER2_PASSWORD = "Password123";
    private static final String USER2_FIRST_NAME = "firstName2";
    private static final String USER2_LAST_NAME = "lastName2";
    private static final LocalDate USER2_DATE_OF_BIRTH = LocalDate.of(2000, 1, 1);
    private static final String USER2_EMAIL = "email2@mail.ca";
    private static final String USER2_PICTURE = "picture2.png";
    private static final boolean USER2_ADMIN = false;

    private static final String VALID_USERNAME = "validUsername";
    private static final String VALID_EMAIL = "valid@mail.ca";


    @BeforeEach
    public void setMockOutput() {
        lenient().when(userRepository.findByUsername(anyString())).thenAnswer((invocation) -> {
            if (invocation.getArgument(0).equals(USER_USERNAME)) {
                return Optional.of(
                        new User(USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME,
                                USER_DATE_OF_BIRTH, USER_EMAIL, USER_PICTURE, USER_ADMIN));
            }else if(invocation.getArgument(0).equals(USER2_USERNAME)) {
                return Optional.of(
                        new User(USER2_USERNAME, USER2_PASSWORD, USER2_FIRST_NAME, USER2_LAST_NAME,
                                USER2_DATE_OF_BIRTH, USER2_EMAIL, USER2_PICTURE, USER2_ADMIN));
            }
            else {
                return Optional.empty();
            }
        });
        lenient().when(userRepository.findAll()).thenAnswer((invocation) ->
                List.of(userRepository.findByUsername(USER_USERNAME).get()));
        lenient().when(userRepository.existsByUsername(anyString())).thenAnswer((invocation) ->
                (invocation.getArgument(0).equals(USER_USERNAME))
                        || (invocation.getArgument(0).equals(USER2_USERNAME)));
        lenient().when(userRepository.existsByEmail(anyString())).thenAnswer((invocation) ->
                (invocation.getArgument(0).equals(USER_EMAIL)
                        || (invocation.getArgument(0).equals(USER2_EMAIL))));
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(userRepository.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(userRepository).delete(any(User.class));
    }

    @Test
    public void createUserSuccess() {
        UserDTO userDTO = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            User user = userService.createOrEditUser(userDTO, true);
            assertEquals(userDTO.getUsername(), user.getUsername());
            assertEquals(userDTO.getPassword(), user.getPassword());
            assertEquals(userDTO.getFirstName(), user.getFirstName());
            assertEquals(userDTO.getLastName(), user.getLastName());
            assertEquals(userDTO.getDateOfBirth(), user.getDateOfBirth());
            assertEquals(userDTO.getEmail(), user.getEmail());
            assertEquals(userDTO.getPicture(), user.getPicture());
            assertEquals(userDTO.isAdmin(), user.isAdmin());
        } catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void createUserTakenUsername() {
        UserDTO user = new UserDTO(USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Username "+ USER_USERNAME +" already taken", e.getMessage() );
        }
    }

    @Test
    public void createUserTakenEmail() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, USER_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Email "+ user.getEmail() +" already taken", e.getMessage() );
        }
    }

    @Test
    public void createUserInvalidEmail() {
        UserDTO userDTO = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, "email", USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(userDTO, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("email must be a well-formed email address", e.getMessage() );
        }
    }

    @Test
    public void createUserShortPassword() {
        String invalidPassword = "Short1";
        UserDTO user = new UserDTO(VALID_USERNAME, invalidPassword, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Password must have at least 8 characters", e.getMessage());
        }
    }

    @Test
    public void createUserLongPassword() {
        String invalidPassword = "VeryLongPasswordTest1234568910";
        UserDTO user = new UserDTO(VALID_USERNAME, invalidPassword, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Password must not have more than 20 characters", e.getMessage());
        }
    }

    @Test
    public void createUserNoLowerCasePassword() {
        String invalidPassword = "PASSWORD123";
        UserDTO user = new UserDTO(VALID_USERNAME, invalidPassword, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Password must contain at least one lowercase character", e.getMessage());
        }
    }

    @Test
    public void createUserNoUpperCasePassword() {
        String invalidPassword = "password123";
        UserDTO user = new UserDTO(VALID_USERNAME, invalidPassword, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Password must contain at least one uppercase character", e.getMessage());
        }
    }

    @Test
    public void createUserNoNumericPassword() {
        String invalidPassword = "Password";
        UserDTO user = new UserDTO(VALID_USERNAME, invalidPassword, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Password must contain at least one numeric character", e.getMessage());
        }
    }

    @Test
    public void createUserBlankUsername() {
        UserDTO user = new UserDTO("", USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("username must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserNullUsername() {
        UserDTO user = new UserDTO(null, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("username must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserBlankPassword() {
        UserDTO user = new UserDTO(VALID_USERNAME, "", USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("password must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserNullPassword() {
        UserDTO user = new UserDTO(VALID_USERNAME, null, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("password must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserBlankFirstName() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, "",
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("firstName must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserNullFirstName() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, null,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("firstName must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserBlankLastName() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                "", USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("lastName must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserNullLastName() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                null, USER_DATE_OF_BIRTH, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("lastName must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserNullDateOfBirth() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, null, VALID_EMAIL, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("dateOfBirth must not be null", e.getMessage());
        }
    }

    @Test
    public void createUserBlankEmail() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, "", USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("email must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserNullEmail() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, null, USER_PICTURE, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("email must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserBlankPicture() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, "", USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("picture must not be blank", e.getMessage());
        }
    }

    @Test
    public void createUserNullPicture() {
        UserDTO user = new UserDTO(VALID_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
                USER_LAST_NAME, USER_DATE_OF_BIRTH, VALID_EMAIL, null, USER_ADMIN);
        try{
            userService.createOrEditUser(user, true);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("picture must not be blank", e.getMessage());
        }
    }

    @Test
    public void editUserSuccess(){
        UserDTO userDTO = new UserDTO(USER_USERNAME, "New Password123", "New First Name",
                "New Last name", LocalDate.of(2001,1,1), "new@mail.ca",
                "newPicture.png", false);
        try{
            User user = userService.createOrEditUser(userDTO, false);
            assertEquals(userDTO.getUsername(), user.getUsername());
            assertEquals(userDTO.getPassword(), user.getPassword());
            assertEquals(userDTO.getFirstName(), user.getFirstName());
            assertEquals(userDTO.getLastName(), user.getLastName());
            assertEquals(userDTO.getDateOfBirth(), user.getDateOfBirth());
            assertEquals(userDTO.getEmail(), user.getEmail());
            assertEquals(userDTO.getPicture(), user.getPicture());
            assertEquals(userDTO.isAdmin(), user.isAdmin());
        } catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void editUserEmailTaken(){
        UserDTO user = new UserDTO(USER_USERNAME, "New Password123", "New First Name",
                "New Last name", LocalDate.of(2001,1,1), USER2_EMAIL,
                "newPicture.png", false);
        try{
            userService.createOrEditUser(user, false);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Email "+ user.getEmail() +" already taken", e.getMessage() );
        }
    }

    @Test
    public void getUserSuccess(){
        try{
            User user = userService.getUser(USER_USERNAME);
            assertEquals(USER_USERNAME, user.getUsername());
            assertEquals(USER_PASSWORD, user.getPassword());
            assertEquals(USER_FIRST_NAME, user.getFirstName());
            assertEquals(USER_LAST_NAME, user.getLastName());
            assertEquals(USER_DATE_OF_BIRTH, user.getDateOfBirth());
            assertEquals(USER_EMAIL, user.getEmail());
            assertEquals(USER_PICTURE, user.getPicture());
            assertEquals(USER_ADMIN, user.isAdmin());
        }catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void getAllUsersSuccess(){
        try{
            List<User> users = userService.getUsers();
            assertEquals(1, users.size());
            assertEquals(USER_USERNAME, users.get(0).getUsername());
            assertEquals(USER_PASSWORD, users.get(0).getPassword());
            assertEquals(USER_FIRST_NAME, users.get(0).getFirstName());
            assertEquals(USER_LAST_NAME, users.get(0).getLastName());
            assertEquals(USER_DATE_OF_BIRTH, users.get(0).getDateOfBirth());
            assertEquals(USER_EMAIL, users.get(0).getEmail());
            assertEquals(USER_PICTURE, users.get(0).getPicture());
            assertEquals(USER_ADMIN, users.get(0).isAdmin());
        }catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void getUserNotFound(){
        try{
            userService.getUser(VALID_USERNAME);
        }catch (IllegalArgumentException e) {
            assertEquals("User "+ VALID_USERNAME +" not found", e.getMessage());
        }
    }

    @Test
    public void deleteUserSuccess(){
        try{
            userService.deleteUser(USER_USERNAME);
        }catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void deleteUserNotFound(){
        try{
            userService.deleteUser(VALID_USERNAME);
        }catch (IllegalArgumentException e) {
            assertEquals("User "+ VALID_USERNAME +" not found", e.getMessage());
        }
    }

}
