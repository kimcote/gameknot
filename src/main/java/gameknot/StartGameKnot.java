
package gameknot;

import static java.lang.System.exit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gameknot.controller.Controller;

@SpringBootApplication
public class StartGameKnot implements CommandLineRunner {
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		SpringApplication.run(StartGameKnot.class, args);
//		
//		
//	}
	
	@Autowired
	private Controller controller;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StartGameKnot.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        
        controller.mainProcess();
        
        exit(0);
    }

}
