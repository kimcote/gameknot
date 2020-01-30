package gameknot.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class KingSlayersSetup {
	
	@Autowired
	private KingSlayers kingslayers;

	public void setupKingSlayers(int KSPlayers) {
		Player player;
		List<Player> players = new ArrayList<Player>();
		
		for (int i=1;i<=KSPlayers;i++) {
			player=new Player("KS"+i, 1400+(100*(i-1)), "0", true);
			player.setRatingNinetyDay(player.getRating()+50);
			players.add(player);
		
		}
		
//		Mockito.when(JSONUtils.getPlayersFromTableData(Mockito.anyString())).thenReturn(players);
		kingslayers.setPlayers(players);
		
		assertEquals(KSPlayers,kingslayers.getMatchablePlayers());
	}
	
	public Player setupOppPlayer(String name, int rating, String pending) {
		Player player=new Player(name, rating, pending, true);
		
		player.setRatingNinetyDay(rating+50);
		return player;
	}
}
