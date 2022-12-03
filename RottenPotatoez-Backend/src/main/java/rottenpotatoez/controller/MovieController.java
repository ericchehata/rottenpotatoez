package rottenpotatoez.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rottenpotatoez.dto.ImportMoviesDTO;
import rottenpotatoez.dto.MovieDTO;
import rottenpotatoez.service.MovieService;
import rottenpotatoez.utils.Conversions;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @PostMapping(value = {"/import"})
    public ResponseEntity<?> importMovies(@ModelAttribute ImportMoviesDTO importMoviesDTO) throws IOException {


        XSSFWorkbook workbook = new XSSFWorkbook(importMoviesDTO.getFile().getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();

        List<MovieDTO> movieDTOList = new ArrayList<>();
        for(int i=1; i<rows; i++) {
            Row row = CellUtil.getRow(i, sheet);
            String title = null;
            try {
                title = row.getCell(0).getStringCellValue();
            }catch (IllegalStateException e){
                title = String.valueOf((int) row.getCell(0).getNumericCellValue());
            }
            int duration = (int) row.getCell(1).getNumericCellValue();
            LocalDate releaseDate = row.getCell(2).getDateCellValue().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            String image = row.getCell(3).getStringCellValue();
            String rating = row.getCell(4).getStringCellValue();
            ArrayList <String> genres = new ArrayList<>();
            for(int j=5; j<row.getPhysicalNumberOfCells(); j++) {
                if (row.getCell(j).getCellType() != Cell.CELL_TYPE_BLANK) {
                    genres.add(row.getCell(j).getStringCellValue());
                }
            }

            try{
                MovieDTO movieDTO = new MovieDTO(null, title, duration, releaseDate, image, rating, genres);
                movieDTOList.add(Conversions.convertToDTO(movieService.createOrEditMovie(movieDTO)));
                System.out.println("Movie added: " + movieDTO.getTitle());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return ResponseEntity.ok(movieDTOList);
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
