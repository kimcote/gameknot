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
		
		Player player1 = new Player("Player1", 1400, "1");
		players.add(player1);
		
		Player player2 = new Player("Player2", 1500, "2");
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
	
	@Test
	public void PlayerAvailable() {
		
		for (Player p: oppTeam.getPlayers()) {
			p.setPending(true);
		}
		
		for (Player p: oppTeam.getPlayers()) {
			p.setCloseRating(true);
		}
		
		assertFalse(oppTeam.isMatchable());
		assertEquals(0,oppTeam.getMatchablePlayers());
		
		for (Player p: oppTeam.getPlayers()) {
			p.setPending(false);
		}
		
		assertTrue(oppTeam.isMatchable());
		assertEquals(2,oppTeam.getMatchablePlayers());
	}
	
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
		
		for (Player p: oppTeam.getPlayers()) {
			p.setCloseRating(true);
		}
		
		assertFalse(oppTeam.isMatchable());
		assertEquals(0,oppTeam.getMatchablePlayers());
		
		for (Player p: oppTeam.getPlayers()) {
			p.setPending(false);
		}
		
		assertTrue(oppTeam.isMatchable());
		assertEquals(2,oppTeam.getMatchablePlayers());
	}
	
	@Test
	public void assignCloseRating() {
		KingSlayers ks = new KingSlayers();
			
	}
	
	@Test
	public void assignAboveGameLimit () {
		
		for (Player p: oppTeam.getPlayers()) {
			p.setCloseRating(true);
		}
		
		oppTeam.assignAboveGameLimit(1);
		
		assertFalse(oppTeam.getPlayers().get(0).isAboveGameLimit());
		assertTrue(oppTeam.getPlayers().get(1).isAboveGameLimit());
		
		assertTrue(oppTeam.getPlayers().get(0).isMatchable());
		assertFalse(oppTeam.getPlayers().get(1).isMatchable());
	}
	
	@Test
	public void getInfoGameLimit() {
		Player player;
		
		players = new ArrayList<Player>();
		
		for (int i=1;i<=10;i++) {
			int rating=1400+(100*(i-1));
			player=new Player("Opp"+i, rating, rating+50);
			players.add(player);
		}
		
		oppTeam = new OppositionTeam("OppTeam",1,players);
		
		oppTeam.getPlayers().get(0).setPending(true);
		oppTeam.getPlayers().get(1).setPending(true);
		
		oppTeam.getPlayers().get(0).setPending(true);
		oppTeam.getPlayers().get(1).setPending(true);
		
		for (int i =5;i<10;i++) {
			oppTeam.getPlayers().get(i).setCloseRating(true);
		}
		
		oppTeam.getPlayers().get(5).setMatched(true);
		oppTeam.getPlayers().get(6).setMatched(true);
		oppTeam.getPlayers().get(7).setAboveGameLimit(true);
		
		assertEquals(2,oppTeam.getMatchablePlayers());
		
		assertEquals("Rank= 1 Team=OppTeam", oppTeam.getInfoPlayers().substring(0,20));
		assertEquals("Players: Available= 10 Pending= 2 CloseRating= 5",oppTeam.getInfoPlayers().substring(20).trim());
		assertEquals("Players: AboveGameLimit=1 Matchable=2", oppTeam.getInfoGameLimit().substring(21).trim());
	}
}
