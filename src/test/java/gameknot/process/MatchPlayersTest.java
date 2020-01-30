package gameknot.process;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import gameknot.model.MatchParameters;
import gameknot.model.ModelConfig;
import gameknot.model.OppositionTeam;
import gameknot.model.Player;
import gameknot.utils.UtilsMockConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={MatchService.class, ModelConfig.class, UtilsMockConfig.class})
public class MatchPlayersTest extends KingSlayersSetup{
	
	@Autowired
    private KingSlayers kingslayers;
	
	@Autowired
	private MatchParameters matchParams;
	
	@MockBean
	private Ladder ladder;
	
	@Test
	public void matchDiffRatingDiffs() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		players.add(setupOppPlayer("Opp1", 1410,"0"));
		players.add(setupOppPlayer("Opp2", 1550,"0"));
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));

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
	public void matchOneAtATime_EqualDiff() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		players.add(setupOppPlayer("Opp1", 1450,"0"));
		players.add(setupOppPlayer("Opp2", 1450,"0"));
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));
		
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
		
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		players.add(setupOppPlayer("Player1", 1430, "0"));
		players.add(setupOppPlayer("Player2", 1460, "0"));
		players.add(setupOppPlayer("Player3", 1410, "0"));
		oppositionTeams.add(new OppositionTeam("OppTeam", 1, players));

		assertEquals(3,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals("Player3", kingslayers.getPlayers().get(0).getBestMatch().getName());
		assertEquals("Player2", kingslayers.getPlayers().get(1).getBestMatch().getName());
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
		
		List<Player> players = new ArrayList<Player>();
		players.add(setupOppPlayer("OppTeam1Player1", 1440, "0"));
		players.add(setupOppPlayer("OppTeam1Player2", 1540, "0"));
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));
		
		players = new ArrayList<Player>();
		players.add(setupOppPlayer("OppTeam2Player1", 1640, "0"));
		players.add(setupOppPlayer("OppTeam2Player2", 1740, "0"));
		oppositionTeams.add(new OppositionTeam("OppTeam2", 2, players));

		assertEquals(2,oppositionTeams.size());
		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		assertEquals(2,oppositionTeams.get(1).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(0,kingslayers.getMatchablePlayers());
	}
	
	@Test
	public void match90Day() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		Player player;
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		
		player=setupOppPlayer("Opp1", 1450, "0");
		player.setRatingNinetyDay(1450);
		players.add(player);
		
		player=setupOppPlayer("Opp2", 1550, "0");
		player.setRatingNinetyDay(1550);
		players.add(player);
		
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));

		assertEquals(2,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,true);
		
		assertEquals(0,kingslayers.getMatchablePlayers());
		assertEquals("Opp1",kingslayers.getPlayers().get(0).getBestMatch().getName());
		assertEquals("Opp2",kingslayers.getPlayers().get(1).getBestMatch().getName());
	}
	
	@Test
	public void matchNinetyDayHigherLowerNormal_NoMatch() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		Player player;
		setupKingSlayers(2);
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		
		player=setupOppPlayer("Opp1", 1400, "0");
		player.setRatingNinetyDay(1450);
		players.add(player);
		
		player=setupOppPlayer("Opp2", 1500, "0");
		player.setRatingNinetyDay(1550);
		players.add(player);
		
		oppositionTeams.add(new OppositionTeam("OppTeam1",1, players));

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
	public void matchMustMatch() throws IOException, InterruptedException { //throws IOException, InterruptedException {
		Player player;
		setupKingSlayers(3);
		
		Player mustMatch=kingslayers.getPlayers().get(2);
		mustMatch.setMustMatch(true);
		kingslayers.setMustMatch(mustMatch.getName());
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		
		List<Player> players = new ArrayList<Player>();
		
		player=setupOppPlayer("Opp1", 1400, "0");
		players.add(player);
		
		player=setupOppPlayer("Opp2", 1500, "0");
		players.add(player);
		
		player=setupOppPlayer("Opp3", 1650, "0");
		players.add(player);
		
		oppositionTeams.add(new OppositionTeam("OppTeam1", 1, players));

		assertEquals(3,oppositionTeams.get(0).getMatchablePlayers());
		
		Mockito.when(ladder.getOppositionTeams()).thenReturn(oppositionTeams);
		
		matchParams.run(50,false,false);
		
		assertEquals(1,kingslayers.getMatchablePlayers());
		assertEquals("Opp3",mustMatch.getBestMatch().getName());
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
