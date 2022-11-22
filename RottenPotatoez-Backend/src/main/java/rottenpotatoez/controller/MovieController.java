package rottenpotatoez.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rottenpotatoez.dto.MovieDTO;
import rottenpotatoez.service.MovieService;
import rottenpotatoez.utils.Conversions;

import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private MovieService movieService;

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody MovieDTO movieDTO) {
        try {
            return ResponseEntity.ok(Conversions.convertToDTO(movieService.createOrEditMovie(movieDTO)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> editMovie(@RequestBody MovieDTO movieDTO) {
        try {
            return ResponseEntity.ok(Conversions.convertToDTO(movieService.createOrEditMovie(movieDTO)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable UUID id){
        try{
            return ResponseEntity.ok(Conversions.convertToDTO(movieService.getMovie(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getMovies(){
        try{
            return ResponseEntity.ok(movieService.getMovies()
                    .stream()
                    .map(Conversions::convertToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable UUID id){
        try{
            movieService.deleteMovie(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
