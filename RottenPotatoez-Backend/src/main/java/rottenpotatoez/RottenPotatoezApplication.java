package rottenpotatoez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RottenPotatoezApplication {

	public static void main(String[] args) {
		SpringApplication.run(RottenPotatoezApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting(){
		return "Welcome to Rotten Potatoez!";
	}


}
