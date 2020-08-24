package gameknot.model;

import java.util.List;

import org.springframework.stereotype.Component;

import gameknot.utils.JSONUtils;
import lombok.Getter;
import lombok.Setter;

@Component
public class Team {

	@Getter @Setter
	protected List<Player> players;
	
	@Getter @Setter
	protected int rank;
	
	@Getter @Setter
    protected String link;
	
	public int getMatchablePlayers() {
		int count=0;
		
		for (Player player:players) {
			
			if (player.isMatchable()) count++;
		}
		
		return count;
	}
	
	public void assignPlayers() {
		this.players = JSONUtils.getPlayersFromTeamLink(this.link);
		
		for (Player p: this.players) {
			p.setCloseRating(true);
		}
    }
}
