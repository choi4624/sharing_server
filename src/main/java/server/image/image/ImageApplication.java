package server.image.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/* 
 * image server for i4share server 
 * based on mysql DB > real file URI 
 * maybe 
 */

@SpringBootApplication
public class ImageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageApplication.class, args);
	}

}
