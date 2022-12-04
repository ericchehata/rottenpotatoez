package rottenpotatoez.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rottenpotatoez.dto.UserDTO;
import rottenpotatoez.model.User;
import rottenpotatoez.service.UserService;
import rottenpotatoez.utils.Conversions;

import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(Conversions.convertToDTO(userService.createOrEditUser(userDTO, true)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(Conversions.convertToDTO(userService.createOrEditUser(userDTO, false)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username){
        try{
            return ResponseEntity.ok(Conversions.convertToDTO(userService.getUser(username)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        try{
            return ResponseEntity.ok(userService.getUsers()
                    .stream()
                    .map(Conversions::convertToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        try{
            userService.deleteUser(username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.login(username, password);
            return ResponseEntity.ok(Conversions.convertToDTO(user));
        }catch(IllegalArgumentException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }


    }

}
