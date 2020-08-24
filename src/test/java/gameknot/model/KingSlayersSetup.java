package gameknot.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KingSlayersSetup {
	
	@Autowired
	private KingSlayers kingslayers;
	
	@Autowired
	private OppositionTeam oppTeam;

	public void setupKingSlayers(int KSPlayers) {
		Player player;
		List<Player> players = new ArrayList<Player>();
		
		for (int i=1;i<=KSPlayers;i++) {
			int rating=1400+(100*(i-1));
			
			player=new Player("KS"+i, rating, rating+50);
			player.setCloseRating(true);
			players.add(player);
		}
		
		kingslayers.setPlayers(players);
		kingslayers.setMustMatch(null);
		
		assertEquals(KSPlayers,kingslayers.getMatchablePlayers());
	}
	
	public OppositionTeam setupOppositionTeam(int OppTeamPlayers) {
		Player player;
		List<Player> players = new ArrayList<Player>();
		
		for (int i=1;i<=OppTeamPlayers;i++) {
			int rating=1400+(100*(i-1));
			player=new Player("Opp"+i, rating, rating+50);
			player.setCloseRating(true);
			players.add(player);
		}
		
		OppositionTeam oppTeam = new OppositionTeam("OppTeam",1,players);
		
		assertEquals(OppTeamPlayers,oppTeam.getMatchablePlayers());
		
		return oppTeam;
	}
	
	public Player setupOppPlayer(String name, int rating, String pending) {
		Player player=new Player(name, rating, pending);
		
		player.setRatingNinetyDay(rating+50);
		player.setCloseRating(true);
		return player;
	}
}
