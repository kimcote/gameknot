package gameknot.model;

import java.util.List;

import lombok.Data;

@Data
public class OppositionTeam {

    public OppositionTeam(String name, List<Player> players) {
        super();
        this.name = name;
        this.players = players;
    }
    public OppositionTeam() {
        
    }
    private int rank;
    private String name;
    private List<Player> players;
    private String link;
    private boolean pending;
    private int activeMatches;
    
    public int getCountPlayersAvailable() {
		int count=0;
		
		for (Player player:players) {
			
			if (!player.isPending()) count++;
		}
		
		return count;
	}
}
