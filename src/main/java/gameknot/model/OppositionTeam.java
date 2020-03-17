package gameknot.model;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;

import gameknot.utils.JSONUtils;
import lombok.Getter;
import lombok.Setter;

@Component
public class OppositionTeam extends Team {
	
	public OppositionTeam() {}

    public OppositionTeam(String name, int rank, List<Player> players) {
        this.name = name;
        this.rank=rank;
        this.players = players;
    }
    
    public OppositionTeam(String name, int rank, String link) {
        this.name = name;
        this.rank=rank;
        this.link=link;
    }
    
    @Getter @Setter
    private String name;
    
    @Setter
    private boolean pending;
    
    @Setter
    private boolean wrongRank;
    
    public boolean isMatchable () {
    	return !pending && !wrongRank && getPendingGames()<20 
    		&& (players==null || getMatchablePlayers()>1);
    }
    public String getUnMatchableReasons () {
    	String s="";
    	if (!pending) s+="Pending";
    	if (!wrongRank) s+=" WrongRank";
    	if (getPendingGames()>=20) s+=" PendingGames="+getPendingGames();
    	if (getMatchablePlayers()<2) s+=" Matchable PLayers="+getMatchablePlayers();
    	return s;
    }
    
    public String getInfo() {
    	return "Rank=" + this.getRank() 
		+ " Team="+StringUtils.rightPad(this.getName(),45)
		+ " Pending Games="+this.getPendingGames()
//					+ " Active Matches="+this.getActiveMatches() 
		+ " Available Players="+this.players.size()
		+ " Pending Players="+this.getPendingPlayers()
		+ " Above Game Limit Players=" + this.getAboveGameLimitPlayers()
		+ " Close Rating Players="+this.getCloseRatingPlayers()
		+ " Matchable Players="+this.getMatchablePlayers();
    }
    
    public int getMatchablePlayers() {
		int count=0;
		
		if (players != null) { 

			for (Player player:players) { 
			
				if (player.isMatchable()) count++;
			}
		}
  
		return count;
	}
    
    public int getCloseRatingPlayers() {
		int count=0;
		
		if (players != null) { 

			for (Player player:players) { 
			
				if (player.isCloseRating()) count++;
			}
		}
  
		return count;
	}
    
    public int getPendingPlayers() {
		int count=0;
		
		if (players != null) { 

			for (Player player:players) { 
			
				if (player.isPending()) count++;
			}
		}
  
		return count;
	}
    
    public int getPendingGames() {
    	
    	int count=0;
		
		if (players != null) { 

			for (Player player:players) { 
			
				count=count+player.getPendingGames();
			}
		}
  
		return count;
    }
    
    public void assignPlayers() {
		this.players = JSONUtils.getPlayersFromTeamLink(this.link);
    }

	public int getAboveGameLimitPlayers() {
		int count=0;
		
		if (players != null) { 

			for (Player player:players) { 
			
				if (player.isAboveGameLimit()) count++;
			}
		}
  
		return count;
	}

	public int getMatchedPlayers() {
		int count=0;
		
		if (players != null) { 

			for (Player player:players) { 
			
				if (player.isMatched()) count++;
			}
		}
  
		return count;
	}

//	public int getAvailablePlayers() {
//		int count=0;
//		
//		if (players != null) { 
//
//			for (Player player:players) { 
//			
//				if (player.isAvailable()) count++;
//			}
//		}
//  
//		return count;
//	}
}
