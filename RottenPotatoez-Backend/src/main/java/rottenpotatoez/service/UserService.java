package rottenpotatoez.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.UserRepository;
import rottenpotatoez.dto.UserDTO;
import rottenpotatoez.model.User;
import rottenpotatoez.utils.Conversions;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User createOrEditUser(UserDTO userDTO, boolean isCreate){
        User user = Conversions.convertToModel(userDTO);
        validateUser(user, isCreate);
        userRepository.save(user);
        return user;
    }

    public User getUser(String username){
        return userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("User "+ username +" not found"));
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(String username){
        User user = getUser(username);
        userRepository.delete(user);
    }

    private void validateUser(User user, boolean isCreate){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if(violations.size() > 0){
            ConstraintViolation<User> violation = violations.iterator().next();
            throw new IllegalArgumentException(violation.getPropertyPath()+" "+violation.getMessage());
        }

        //Username validation
        if(isCreate && userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username "+ user.getUsername() +" already taken");
        }

        //Email validation
        if(isCreate && userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email "+ user.getEmail() +" already taken");
        }else if(!isCreate
                && !user.getEmail().equals(userRepository.findByUsername(user.getUsername()).get().getEmail())
                && userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email "+ user.getEmail() +" already taken");
        }
        //Password validation
        if (user.getPassword().length()<8)
            throw new IllegalArgumentException("Password must have at least 8 characters");
        if (user.getPassword().length()>20)
            throw new IllegalArgumentException("Password must not have more than 20 characters");

        boolean upperCaseFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;

        for(int i = 0; i< user.getPassword().length(); i++) {
            if(Character.isUpperCase(user.getPassword().charAt(i))) upperCaseFlag = true;
            else if(Character.isLowerCase(user.getPassword().charAt(i))) lowerCaseFlag = true;
            else if(Character.isDigit(user.getPassword().charAt(i))) numberFlag = true;
        }

        if(!upperCaseFlag)
            throw new IllegalArgumentException ("Password must contain at least one uppercase character");
        if(!lowerCaseFlag)
            throw new IllegalArgumentException ("Password must contain at least one lowercase character");
        if(!numberFlag)
            throw new IllegalArgumentException ("Password must contain at least one numeric character");

    }
}
