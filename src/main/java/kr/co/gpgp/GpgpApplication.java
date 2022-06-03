package kr.co.gpgp;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptableProperties
@SpringBootApplication
public class GpgpApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpgpApplication.class, args);
	}

}
