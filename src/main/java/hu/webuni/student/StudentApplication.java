package hu.webuni.student;

import hu.webuni.student.service.InitDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@RequiredArgsConstructor
//@SpringBootApplication
//ha security nélkül akarjuk futtatni:
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StudentApplication implements CommandLineRunner{


	@Autowired
	InitDbService initDbService;

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		// airportService.createFlight();
//		System.out.println(priceService.getFinalPrice(200));
//		System.out.println(priceService.getFinalPrice(20000));

//		initDbService.createUsersIfNeeded();

		initDbService.addInitData();

	}

//teszt



}
