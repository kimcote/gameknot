package gameknot.model;

import static org.junit.Assert.assertEquals;

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

import gameknot.services.MatchParameters;
import gameknot.services.ProcessMockConfig;
import gameknot.utils.UtilsConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={ModelConfig.class, ProcessMockConfig.class, UtilsConfig.class})
public class KingSlayerTest extends KingSlayersSetup{
	
	@Autowired
	private KingSlayers kingslayers;
	
	@Autowired
	private MatchParameters matchparams;
	
	@Before
	public void init() {
		matchparams.setMaxDiff(50);
		matchparams.setHigherNinetyDayLowerNormal(false);
		matchparams.setNinetyDay(false);
	}

	@Test
	public void assignBestMatch_KS1_Opp1() throws IOException, InterruptedException {
		
		setupKingSlayers(1);
		
		List<Player> players = new ArrayList<Player>();
		
		Player opp1=setupOppPlayer("Opp1", 1400, "0");
		players.add(opp1);
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		kingslayers.assignBestMatch(matchparams, oppTeam);
		
		assertEquals(opp1, kingslayers.getPlayers().get(0).getBestMatch());
	}
	
	@Test
	public void assignBestMatch_KS3_Opp2() throws IOException, InterruptedException {
		
		setupKingSlayers(3);
		
		List<Player> players = new ArrayList<Player>();
		
		Player opp1=setupOppPlayer("Opp1", 1400, "0");
		opp1.setRatingNinetyDay(opp1.getRating()+50);
		players.add(opp1);
		
		Player opp2=setupOppPlayer("Opp2", 1500, "0");
		opp2.setRatingNinetyDay(opp2.getRating()+50);
		players.add(opp2);
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		kingslayers.assignBestMatch(matchparams,oppTeam);
		
		assertEquals(opp1, kingslayers.getPlayers().get(0).getBestMatch());
		assertEquals(opp2, kingslayers.getPlayers().get(1).getBestMatch());
		assertEquals(null, kingslayers.getPlayers().get(2).getBestMatch());
	}
	
	@Test
	public void getBestMatched_KS2_Opp2() throws IOException, InterruptedException {
		
		setupKingSlayers(2);
		Player opp1=setupOppPlayer("Opp1", 1410, "0");
		opp1.setRatingNinetyDay(opp1.getRating()+50);
		
		Player opp2=setupOppPlayer("Opp2", 1500, "0");
		opp2.setRatingNinetyDay(opp2.getRating()+50);
		
		Player ks1 = kingslayers.getPlayers().get(0);
		ks1.setBestMatch(opp1);
		
		Player ks2 = kingslayers.getPlayers().get(1);
		ks2.setBestMatch(opp2);
		
		Player player = kingslayers.getBestMatched();
		
		assertEquals(ks2, player);
	}
	
	@Test
	public void KingSlayer_getBestMatched_MustMatch() throws IOException, InterruptedException {
		
		setupKingSlayers(2);
		Player opp1=setupOppPlayer("Opp1", 1410, "0");
		opp1.setRatingNinetyDay(opp1.getRating()+50);
		
		Player opp2=setupOppPlayer("Opp2", 1500, "0");
		opp2.setRatingNinetyDay(opp2.getRating()+50);
		
		Player ks1 = kingslayers.getPlayers().get(0);
		ks1.setMustMatch(true);
		ks1.setBestMatch(opp1);
		
		Player ks2 = kingslayers.getPlayers().get(1);
		ks2.setBestMatch(opp2);
		
		Player player = kingslayers.getBestMatched();
		
		assertEquals(ks1, player);
	}
	
	@Test
	public void assignBestMatch_HigherNinetyDayLowerNormal_MatchNinetyDay() throws IOException, InterruptedException {
		
		setupKingSlayers(3);
		
		List<Player> players = new ArrayList<Player>();
		
		Player opp1=setupOppPlayer("Opp1", 1410, "0");
		opp1.setRatingNinetyDay(1440);
		players.add(opp1);
		
		Player opp2=setupOppPlayer("Opp2", 1510, "0");
		opp2.setRatingNinetyDay(1540);
		players.add(opp2);
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		matchparams.setMaxDiff(50);
		matchparams.setHigherNinetyDayLowerNormal(true);
		matchparams.setNinetyDay(true);
		kingslayers.assignBestMatch(matchparams,oppTeam);
		
		assertEquals(opp1, kingslayers.getPlayers().get(0).getBestMatch());
		assertEquals(opp2, kingslayers.getPlayers().get(1).getBestMatch());
		assertEquals(null, kingslayers.getPlayers().get(2).getBestMatch());
	}
	
	@Test
	public void assignBestMatch_HigherNinetyDayLowerNormal_MatchNormal() throws IOException, InterruptedException {
		
		setupKingSlayers(3);
		
		List<Player> players = new ArrayList<Player>();
		
		Player opp1=setupOppPlayer("Opp1", 1410, "0");
		opp1.setRatingNinetyDay(1440);
		players.add(opp1);
		
		Player opp2=setupOppPlayer("Opp2", 1510, "0");
		opp2.setRatingNinetyDay(1540);
		players.add(opp2);
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
		
		matchparams.setMaxDiff(50);
		matchparams.setHigherNinetyDayLowerNormal(true);
		matchparams.setNinetyDay(false);
		
		kingslayers.assignBestMatch(matchparams,oppTeam);
		
		assertEquals(opp1, kingslayers.getPlayers().get(0).getBestMatch());
		assertEquals(opp2, kingslayers.getPlayers().get(1).getBestMatch());
		assertEquals(null, kingslayers.getPlayers().get(2).getBestMatch());
	}
	
//	@Test
//	public void assignBestMatch_NormalMatch_NinetyDayGtrMaxDiff() throws IOException, InterruptedException {
//		
//		setupKingSlayers(3);
//		
//		List<Player> players = new ArrayList<Player>();
//		
//		Player opp1=setupOppPlayer("Opp1", 1400, "0");
//		opp1.setRatingNinetyDay(1550);
//		players.add(opp1);
//		
//		Player opp2=setupOppPlayer("Opp2", 1500, "0");
//		opp2.setRatingNinetyDay(1650);
//		players.add(opp2);
//		
//		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
//		
//		matchparams.setHigherNinetyDayLowerNormal(true);
//		matchparams.setNinetyDay(false);
//		
//		kingslayers.assignBestMatch(matchparams,oppTeam);
//		
//		assertEquals(null, kingslayers.getPlayers().get(0).getBestMatch());
//		assertEquals(null, kingslayers.getPlayers().get(1).getBestMatch());
//		assertEquals(null, kingslayers.getPlayers().get(2).getBestMatch());
//	}
//	
//	@Test
//	public void assignBestMatch_NinetyMatch_NinetyDayGtrMaxDiff() throws IOException, InterruptedException {
//		
//		setupKingSlayers(3);
//		
//		List<Player> players = new ArrayList<Player>();
//		
//		Player opp1=setupOppPlayer("Opp1", 1400, "0");
//		opp1.setRatingNinetyDay(1501);
//		players.add(opp1);
//		
//		Player opp2=setupOppPlayer("Opp2", 1500, "0");
//		opp2.setRatingNinetyDay(1601);
//		players.add(opp2);
//		
//		OppositionTeam oppTeam = new OppositionTeam("OppTeam1", 1, players);
//		
//		matchparams.setNinetyDay(true);
//		matchparams.setHigherNinetyDayLowerNormal(false);
//		
//		kingslayers.assignBestMatch(matchparams,oppTeam);
//		
//		assertEquals(null, kingslayers.getPlayers().get(0).getBestMatch());
//		assertEquals(null, kingslayers.getPlayers().get(1).getBestMatch());
//		assertEquals(null, kingslayers.getPlayers().get(2).getBestMatch());
//	}
	
	
}
