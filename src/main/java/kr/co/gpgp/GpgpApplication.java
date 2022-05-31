package kr.co.gpgp;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableEncryptableProperties
@SpringBootApplication
public class GpgpApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpgpApplication.class, args);
	}

}
