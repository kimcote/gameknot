package gameknot.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gameknot.utils.UtilsMockConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={OppositionTeam.class, UtilsMockConfig.class})
public class OppositionTeamTest {
	
	private OppositionTeam oppTeam;
	
	private List<Player> players;
	
	@Before
	public void setUpTeam() {
		players = new ArrayList<Player>();
		
		Player player1 = new Player("Player1", 1400, "0");
		player1.setCloseRating(true);
		players.add(player1);
		
		Player player2 = new Player("Player2", 1500, "0");
		player2.setCloseRating(true);
		players.add(player2);
		
		oppTeam = new OppositionTeam("OppTeam1", 1, "link");
		oppTeam.setPlayers(players);
	}
	
	@Test
	public void setPlayers() throws IOException, URISyntaxException, InterruptedException {
//		oppTeam.assignPlayers();
	}
	
	@Test
	public void setPlayers_OppositionTeams() throws IOException, URISyntaxException, InterruptedException {
	
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		OppositionTeam oppTeam1 = new OppositionTeam("OppTeam1", 1, "link");
		
		oppositionTeams.add(oppTeam1);
		
		for (OppositionTeam oppTeam: oppositionTeams) {	
//			oppTeam.assignPlayers();
		}
	}
	
//	@Test
//	public void PlayerAvailable() {
//		
//		for (Player p: oppTeam.getPlayers()) {
//			p.setAvailable(false);
//		}
//		
//		assertFalse(oppTeam.isMatchable());
//		assertEquals(0,oppTeam.getMatchablePlayers());
//		
//		for (Player p: oppTeam.getPlayers()) {
//			p.setAvailable(true);
//		}
//		
//		assertTrue(oppTeam.isMatchable());
//		assertEquals(2,oppTeam.getMatchablePlayers());
//	}
	
	@Test
	public void PlayerCloseRating() {
		
		for (Player p: oppTeam.getPlayers()) {
			p.setCloseRating(false);
		}
		
		assertFalse(oppTeam.isMatchable());
		assertEquals(0,oppTeam.getMatchablePlayers());
		
		for (Player p: oppTeam.getPlayers()) {
			p.setCloseRating(true);
		}
		
		assertTrue(oppTeam.isMatchable());
		assertEquals(2,oppTeam.getMatchablePlayers());
	}
	
	@Test
	public void PlayerPending() {
		
		for (Player p: oppTeam.getPlayers()) {
			p.setPending(true);
		}
		
		assertFalse(oppTeam.isMatchable());
		assertEquals(0,oppTeam.getMatchablePlayers());
		
		for (Player p: oppTeam.getPlayers()) {
			p.setPending(false);
		}
		
		assertTrue(oppTeam.isMatchable());
		assertEquals(2,oppTeam.getMatchablePlayers());
	}
}
