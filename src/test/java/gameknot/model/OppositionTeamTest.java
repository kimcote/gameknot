package gameknot.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gameknot.utils.UtilsMockConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={OppositionTeam.class, UtilsMockConfig.class})
public class OppositionTeamTest {
	
	@Autowired
	private OppositionTeam oppositionTeam;
	
	@Test
	public void setPlayers() throws IOException, URISyntaxException, InterruptedException {
		oppositionTeam.assignPlayers();
	}
	
	@Test
	public void setPlayers_OppositionTeams() throws IOException, URISyntaxException, InterruptedException {
	
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		OppositionTeam oppTeam1 = new OppositionTeam("OppTeam1", 1, "link");
		
		oppositionTeams.add(oppTeam1);
		
		for (OppositionTeam oppTeam: oppositionTeams) {	
			oppTeam.assignPlayers();
		}
	}
}
