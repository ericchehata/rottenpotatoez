package rottenpotatoez.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.UserRepository;
import rottenpotatoez.dto.UserDTO;
import rottenpotatoez.model.User;
import rottenpotatoez.utils.Conversions;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User createOrEditUser(UserDTO userDTO, boolean isCreate){
        validateInputs(userDTO, isCreate);
        return userRepository.save(Conversions.convertToModel(userDTO));
    }

    public User getUser(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User "+ username +" not found"));
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(String username){
        userRepository.deleteById(username);
    }

    private void validateInputs(UserDTO userDTO, boolean isCreate){
        //Username validation
        if(isCreate && userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new IllegalArgumentException("Account with "+ userDTO.getUsername() +" already exists");
        }

        //Email validation
        if(isCreate && userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new IllegalArgumentException("Account with email "+ userDTO.getEmail() +" already exists");
        }
        //Password validation
        if (userDTO.getPassword().length()<8) throw new IllegalArgumentException("Password must have at least 8 characters");
        if (userDTO.getPassword().length()>20) throw new IllegalArgumentException("Password must not have more than 20 characters");

        boolean upperCaseFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;

        for(int i=0; i<userDTO.getPassword().length(); i++) {
            if(Character.isUpperCase(userDTO.getPassword().charAt(i))) upperCaseFlag = true;
            else if(Character.isLowerCase(userDTO.getPassword().charAt(i))) lowerCaseFlag = true;
            else if(Character.isDigit(userDTO.getPassword().charAt(i))) numberFlag = true;
        }

        if(!upperCaseFlag) throw new IllegalArgumentException ("Password must contain at least one uppercase character");
        if(!lowerCaseFlag) throw new IllegalArgumentException ("Password must contain at least one lowercase character");
        if(!numberFlag) throw new IllegalArgumentException ("Password must contain at least one numeric character");

    }
}
