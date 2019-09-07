
package gameknot;

import static java.lang.System.exit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gameknot.model.KingSlayers;
import gameknot.model.OppositionTeams;
import gameknot.process.CreateData;
import gameknot.process.MatchPlayers;

@SpringBootApplication
public class StartGameKnot implements CommandLineRunner {
    
    @Autowired
    private KingSlayers kingslayerPlayers;
    
    @Autowired
    private OppositionTeams oppositionTeams;
    
    @Autowired
    private CreateData createdata;
    
    @Autowired
    private MatchPlayers matchplayers;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StartGameKnot.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        
        createdata = new CreateData();
        createdata.process(kingslayerPlayers, oppositionTeams);
        
        
        matchplayers = new MatchPlayers();
        matchplayers.process(kingslayerPlayers, oppositionTeams);
        
        exit(0);
    }

}
