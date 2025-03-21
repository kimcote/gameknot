package gameknot.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gameknot.model.KingSlayers;
import gameknot.model.KingSlayersSetup;
import gameknot.model.Ladder;
import gameknot.model.ModelConfig;
import gameknot.model.OppositionTeam;
import gameknot.model.Player;
import gameknot.utils.UtilsMockConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={MatchService.class, ModelConfig.class, UtilsMockConfig.class})
public class MatchServicesTest extends KingSlayersSetup{
	
	@Autowired
    private KingSlayers kingslayers;
	
	@Autowired
	private OppositionTeam oppTeam;
	
	@Autowired
	private MatchParameters matchParams;
	
	@MockBean
	private Ladder ladder;
	
	@Before
	public void init() {
		Mockito.reset(ladder);
		oppTeam = new OppositionTeam();
//		kingslayers=new KingSlayers();
	}
	
	@After
	public void cleanUp() {
		kingslayers.setPlayers(null);
		kingslayers.setMustMatch(null);
	}
	
	
	
	@Test
	public void matchDiffRatingDiffs() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		oppTeam = setupOppositionTeam(2);
		oppTeam.getPlayers().get(0).setRating(1450);
		oppTeam.getPlayers().get(1).setRating(1550);
		oppositionTeams.add(oppTeam);

		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(0,kingslayers.getMatchablePlayers());
		assertEquals("Opp1",kingslayers.getPlayers().get(0).getBestMatch().getName());
		assertEquals("Opp2",kingslayers.getPlayers().get(1).getBestMatch().getName());
	}
	
//	@Test
//	public void matchBothTogether_EqualDiff() throws IOException, InterruptedException { //throws IOException, InterruptedException {
//		
//		setupKingSlayers(2);
//		
//		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
//		
//		List<Player> players = new ArrayList<Player>();
//		players.add(new Player("Opp1", 1450, "0"));
//		players.add(new Player("Opp2", 1450, "0"));
//		oppositionTeams.add(new OppositionTeam("OppTeam1", players));
//		
//		
//
//		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
//		
//		matchService.matchBothTogether(50,false,false);
//		
//		assertEquals(0,kingslayers.getMatchablePlayers());
////		assertEquals("Opp1",kingslayers.getPlayers().get(0).getBestMatch().getName());
////		assertEquals("Opp2",kingslayers.getPlayers().get(1).getBestMatch().getName());
//	}
	
	@Test
	public void matchEqualDiff() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		
		setupKingSlayers(2);
		assertEquals(2,kingslayers.getMatchablePlayers());
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		oppTeam = setupOppositionTeam(2);
		oppTeam.getPlayers().get(0).setRating(1450);
		oppTeam.getPlayers().get(1).setRating(1550);
		oppositionTeams.add(oppTeam);
		
		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(0,kingslayers.getMatchablePlayers());
//		assertEquals("Opp1",kingslayers.getPlayers().get(0).getBestMatch().getName());
//		assertEquals("Opp2",kingslayers.getPlayers().get(1).getBestMatch().getName());
	}
	
	@Test
	public void matchDiff51() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		players.add(setupOppPlayer("Opp1", 1349, "0"));
		players.add(setupOppPlayer("Opp2", 1551, "0"));
		oppositionTeams.add(new OppositionTeam("DACII LIBERE", 1, players));

		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(2,kingslayers.getMatchablePlayers());
	}
	
	@Test
	public void matchOneOppTeam_ThreePlayers() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		System.out.println("\nmatchOneOppTeam_ThreePlayers");
		
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("KS1",1400,1450));
		players.add(new Player("KS2",1420,1450));
		players.add(new Player("KS3",1440,1450));
		
		for (Player p:players) {
			p.setCloseRating(true);
		}
		
		kingslayers.setPlayers(players);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		players = new ArrayList<Player>();
		players.add(new Player("Opp1",1450,1450));
		players.add(new Player("Opp2",1430,1450));
		players.add(new Player("Opp3",1410,1450));
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));
		
		for (Player p:oppositionTeams.get(0).getPlayers()) {
			p.setCloseRating(true);
		}

		assertEquals(3,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,true,false);
		
		assertEquals(1,kingslayers.getMatchablePlayers());
		assertEquals("Opp3", kingslayers.getPlayers().get(0).getBestMatch().getName());
		assertEquals("Opp2", kingslayers.getPlayers().get(1).getBestMatch().getName());
	}
	
	@Test
	public void matchTwoKS_TwoOppTeams() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		players.add(setupOppPlayer("OppTeam1Player1", 1450, "0"));
		players.add(setupOppPlayer("OppTeam1Player2", 1550, "0"));
		oppositionTeams.add(new OppositionTeam("OppTeam1",1, players));
		
		players = new ArrayList<Player>();
		players.add(setupOppPlayer("OppTeam2Player1", 1450, "0"));
		players.add(setupOppPlayer("OppTeam2Player2", 1550, "0"));
		oppositionTeams.add(new OppositionTeam("OppTeam2",2, players));

		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		assertEquals(2,oppositionTeams.size());
		
		matchParams.run(50,false,false);
	}
	
	@Test
	public void matchFourKS_TwoOppTeams() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		
		setupKingSlayers(4);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		oppTeam = setupOppositionTeam(2);
		oppTeam.getPlayers().get(0).setRating(1440);
		oppTeam.getPlayers().get(0).setRatingNinetyDay(1490);
		oppTeam.getPlayers().get(1).setRating(1540);
		oppTeam.getPlayers().get(1).setRatingNinetyDay(1590);
		
		oppositionTeams.add(oppTeam);
		
		oppTeam = setupOppositionTeam(2);
		oppTeam.getPlayers().get(0).setRating(1640);
		oppTeam.getPlayers().get(0).setRatingNinetyDay(1690);
		oppTeam.getPlayers().get(1).setRating(1740);
		oppTeam.getPlayers().get(1).setRatingNinetyDay(1790);
		
		oppositionTeams.add(oppTeam);

		assertEquals(2,oppositionTeams.size());
		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		assertEquals(2,oppositionTeams.get(1).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(0,kingslayers.getMatchablePlayers());
	}
	
	@Test
	public void match90Day() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		System.out.println("\nmatch90Day");
		setupKingSlayers(2);
		oppTeam = setupOppositionTeam(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		oppTeam.getPlayers().get(0).setRatingNinetyDay(1450);
		oppTeam.getPlayers().get(1).setRatingNinetyDay(1550);
		
		oppositionTeams.add(oppTeam);

		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,true);
		
		assertEquals(0,kingslayers.getMatchablePlayers());
		assertEquals("Opp1",kingslayers.getPlayers().get(0).getBestMatch().getName());
		assertEquals("Opp2",kingslayers.getPlayers().get(1).getBestMatch().getName());
	}
	
	@Test
	public void match_OppLowerNormalHigher90Day_NoMatch() throws IOException, InterruptedException { 
		System.out.println("\nmatch_OppLowerNormalHigher90Day_NoMatch");
		
		setupKingSlayers(2);
		
		oppTeam = setupOppositionTeam(2);
		oppTeam.getPlayers().get(0).setRating(1399);
		oppTeam.getPlayers().get(0).setRatingNinetyDay(1451);
		oppTeam.getPlayers().get(1).setRating(1499);
		oppTeam.getPlayers().get(1).setRatingNinetyDay(1551);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		oppositionTeams.add(oppTeam);

		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,true,true);
		
		assertEquals(2,kingslayers.getMatchablePlayers());

	}
	
	
	@Test
	public void matchNinetyDayHigherLowerNormal_Match() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		Player player;
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		
		player=setupOppPlayer("Opp1", 1420, "0");
		player.setRatingNinetyDay(1430);
		players.add(player);
		
		player=setupOppPlayer("Opp2", 1520, "0");
		player.setRatingNinetyDay(1530);
		players.add(player);
		
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));

		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,true,true);
		
		assertEquals(0,kingslayers.getMatchablePlayers());

	}
	
	@Test
	public void matchMustMatch_LastKS() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		
		setupKingSlayers(3);
		
		Player mustMatch=kingslayers.getPlayers().get(2);
		mustMatch.setMustMatch(true);
		kingslayers.setMustMatch(mustMatch.getName());
		
		oppTeam = setupOppositionTeam(3);
		oppTeam.getPlayers().get(2).setRating(1650);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		oppositionTeams.add(oppTeam);

		assertEquals(3,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(1,kingslayers.getMatchablePlayers());
		assertEquals("Opp3",mustMatch.getBestMatch().getName());
	}
	
	@Test
	public void matchMustMatch_FirstKS() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		System.out.println("\nmatchMustMatch_FirstKS");
		setupKingSlayers(3);
		
		Player mustMatch=kingslayers.getPlayers().get(0);
		mustMatch.setMustMatch(true);
		kingslayers.setMustMatch(mustMatch.getName());

		oppTeam = setupOppositionTeam(3);
		oppTeam.getPlayers().get(0).setRating(1450);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		oppositionTeams.add(oppTeam);
		assertEquals(3,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		assertEquals(oppositionTeams, ladder.getOppositionTeams());
		
		assertTrue(oppTeam.isMatchable());
		
		matchParams.run(50,false,false);
		
		assertEquals(1,kingslayers.getMatchablePlayers());
		assertEquals("Opp1",mustMatch.getBestMatch().getName());
	}
	
	@Test
	public void match_OppTeamNoMatchablePlayers() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		Player player;
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		
		player=setupOppPlayer("Opp1", 1400, "0 + 2");
		players.add(player);
		
		player=setupOppPlayer("Opp2", 1500, "0 + 2");
		players.add(player);
		
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));

		assertEquals(0,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(2,kingslayers.getMatchablePlayers());
	}
	
	@Test
	public void match_OppTeamLowerRank() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		Player player;
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		
		player=setupOppPlayer("Opp1", 1400, "0 + 2");
		players.add(player);
		
		player=setupOppPlayer("Opp2", 1500, "0 + 2");
		players.add(player);
		
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));

		assertEquals(0,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(2,kingslayers.getMatchablePlayers());
	}
	
	@Test
	public void match_OppTeamIsPending() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		Player player;
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		
		player=setupOppPlayer("Opp1", 1400, "0");
		players.add(player);
		
		player=setupOppPlayer("Opp2", 1500, "0");
		players.add(player);
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		oppTeam.setPending(true);
		oppositionTeams.add(oppTeam);
		
		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(2,kingslayers.getMatchablePlayers());
	}
	
	@Test
	public void match_OppTeam() {
		setupKingSlayers(2);
				
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		players.add(setupOppPlayer("topflyer65", 1580, "0"));
		players.add(setupOppPlayer("scoutyscout", 1344, "0"));
		oppositionTeams.add(new OppositionTeam("Peace, Love, and Bunny Rabbits ", 1, players));
		
		players = new ArrayList<Player>();
		players.add(setupOppPlayer("noggin31", 1161, "0"));
		players.add(setupOppPlayer("brett1955", 1177, "0"));
		oppositionTeams.add(new OppositionTeam("TRITON", 2, players));
		
				
	}
}
