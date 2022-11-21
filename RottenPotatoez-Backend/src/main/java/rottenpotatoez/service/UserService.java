package rottenpotatoez.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.UserRepository;
import rottenpotatoez.dto.UserDTO;
import rottenpotatoez.model.User;
import rottenpotatoez.utils.Conversions;

import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    public User createUser(UserDTO userDTO){
        return userRepository.save(Conversions.convertToModel(userDTO));
    }

    public User editUser(UserDTO userDTO){
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
}
