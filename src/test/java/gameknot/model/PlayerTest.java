package gameknot.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gameknot.process.ProcessMockConfig;
import gameknot.utils.UtilsMockConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={ModelConfig.class, ProcessMockConfig.class, UtilsMockConfig.class})
public class PlayerTest extends KingSlayersSetup {

	@Autowired
	KingSlayers kingslayers;
	
	@Autowired
	private MatchParameters matchParams;
	
	@Before
	public void init() {
		matchParams.setMaxDiff(50);
		matchParams.setHigherNinetyDayLowerNormal(false);
		matchParams.setNinetyDay(false);
	}
	
	@Test
	public void newPlayer() {
		
		Player player = new Player("KS1", 1400, "2", true);
		
		assertEquals("KS1", player.getName());
		assertEquals(1400, player.getRating());
		assertEquals(2, player.getActiveGames());
		assertEquals(false,player.isPending());
		assertEquals(0,player.getPendingGames());
	}
	
	@Test
	public void newPlayer_Pending() {
		
		Player player = new Player("KS2", 1500, "4 + 2", true);
		
		assertEquals("KS2", player.getName());
		assertEquals(1500, player.getRating());
		assertEquals(4, player.getActiveGames());
		assertEquals(true,player.isPending());
		assertEquals(2,player.getPendingGames());
	}
	
	@Test
	public void newPlayer_DoubleFigureGames() {
		
		Player player = new Player("KS3", 1600, "10 + 2", true);
		
		assertEquals("KS3", player.getName());
		assertEquals(1600, player.getRating());
		assertEquals(10, player.getActiveGames());
		assertEquals(true,player.isPending());
		assertEquals(2,player.getPendingGames());
	}
	
	@Test 
	public void closeRatingTrue_FirstKS() {
		
		setupKingSlayers(2);
		
		Player player = new Player("Opp1", 1400, "0", true);
		player.assignCloseRating(kingslayers, 50);
		
		assertFalse(player.isCloseRating());
	}
	
	@Test 
	public void closeRatingTrue_LastKS() {
		
		setupKingSlayers(2);
		
		Player player = new Player("Opp2", 1500, "0", true);
		player.assignCloseRating(kingslayers, 50);
		
		assertFalse(player.isCloseRating());
	}
	
	@Test 
	public void closeRatingFalse() {
		
		setupKingSlayers(2);
		
		Player player = new Player("Opp1", 1551, "0", true);
		player.assignCloseRating(kingslayers, 50);
		
		assertTrue(player.isCloseRating());
	}
	
	@Test
	public void assignBestMatch_MatchNormal_Opp1() throws IOException, InterruptedException {
		
		setupKingSlayers(1);
		
		List<Player> players = new ArrayList<Player>();
		
		players.add(setupOppPlayer("Opp1", 1400, "0"));
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		kingslayers.getPlayers().get(0).assignBestMatch(matchParams, oppTeam);
		
		assertEquals(players.get(0), kingslayers.getPlayers().get(0).getBestMatch());
	}
	
	@Test
	public void assignBestMatch_MatchNormal_Opp2_MatchFirst() throws IOException, InterruptedException {
		
		setupKingSlayers(1);
		
		List<Player> players = new ArrayList<Player>();
		
		players.add(setupOppPlayer("Opp1", 1400, "0"));
		players.add(setupOppPlayer("Opp2", 1500, "0"));
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		kingslayers.getPlayers().get(0).assignBestMatch(matchParams, oppTeam);
		
		assertEquals(players.get(0), kingslayers.getPlayers().get(0).getBestMatch());
	}
	
	@Test
	public void assignBestMatch_MatchNormal_Opp2_MatchLast() throws IOException, InterruptedException {
		
		setupKingSlayers(1);
		
		List<Player> players = new ArrayList<Player>();
		
		players.add(setupOppPlayer("Opp1", 1350, "0"));
		players.add(setupOppPlayer("Opp2", 1400, "0"));
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		kingslayers.getPlayers().get(0).assignBestMatch(matchParams, oppTeam);
		
		assertEquals(players.get(1), kingslayers.getPlayers().get(0).getBestMatch());
	}
	/*
	 * Tests with Ninety90GtrNormal and Ninety MatchParameters
	 */
	@Test
	public void assignBestMatch_HigherNinetyDayLowerNormal_NinetyDay_Opp1_NoMatch() throws IOException, InterruptedException {
		matchParams.setHigherNinetyDayLowerNormal(true);
		matchParams.setNinetyDay(true);
	
		setupKingSlayers(1);
		
		List<Player> players = new ArrayList<Player>();
		
		Player opp1 = setupOppPlayer("Opp1", 1450, "0");
		opp1.setRatingNinetyDay(1450);
		players.add(opp1);
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		kingslayers.getPlayers().get(0).assignBestMatch(matchParams, oppTeam);
		
		assertEquals(null, kingslayers.getPlayers().get(0).getBestMatch());
	}
	
	@Test
	public void assignBestMatch_HigherNinetyDayLowerNormal_NinetyDay_Opp1_Match() throws IOException, InterruptedException {
		matchParams.setHigherNinetyDayLowerNormal(true);
		matchParams.setNinetyDay(true);
	
		setupKingSlayers(1);
		
		List<Player> players = new ArrayList<Player>();
		
		Player opp1 = setupOppPlayer("Opp1", 1450, "0");
		opp1.setRatingNinetyDay(1350);
		players.add(opp1);
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		kingslayers.getPlayers().get(0).assignBestMatch(matchParams, oppTeam);
		
		assertEquals(null, kingslayers.getPlayers().get(0).getBestMatch());
	}
	
	@Test
	public void assignBestMatch_Match90Day_Opp1_Match() throws IOException, InterruptedException {
		matchParams.setHigherNinetyDayLowerNormal(false);
		matchParams.setNinetyDay(true);
		setupKingSlayers(1);
		
		List<Player> players = new ArrayList<Player>();
		
		Player opp1 = setupOppPlayer("Opp1", 1400, "0");
		opp1.setRatingNinetyDay(1450);
		players.add(opp1);
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		kingslayers.getPlayers().get(0).assignBestMatch(matchParams, oppTeam);
		
		assertEquals(players.get(0), kingslayers.getPlayers().get(0).getBestMatch());
	}
}
