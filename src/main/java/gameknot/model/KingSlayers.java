
package gameknot.model;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.services.MatchParameters;
import gameknot.utils.HTMLReader;
import lombok.Getter;

@Component
public class KingSlayers extends Team{
	
	@Autowired
    private MatchParameters matchParams;
	
	@Getter 
    private Player mustMatch;
	
	@Autowired
	public KingSlayers() {
		this.link="/team.pl?chess=912";
	}
    
    public void setMustMatch(String playername) {
    	
    	if (StringUtils.isEmpty(playername)) {
    		
    		if (this.players!=null) {
	    		for (Player player:players) {
	    			player.setMustMatch(false);
	        	}
    		}
			this.mustMatch=null;
			return;
		}
    	
    	for (Player player:players) {
			
			if (player.getName().equals(playername)) {
				this.mustMatch=player;
				player.setMustMatch(true);
				break;
			}
    	}
    }
    
    /*
	 * Get best match for each kingslayer
	 */
    public void assignBestMatch(MatchParameters match, OppositionTeam oppTeam) throws IOException, InterruptedException {
    	
    	for (Player ksPlayer: players) {
	        
	    	if (ksPlayer.isMatchable()) {
	    		ksPlayer.assignBestMatch(match, oppTeam);
	    	}
		}
	}
    
    
    /*
	 * Get lowest best match from all Kingslayers matches 
	 */ 
    public Player getBestMatched() {
    	Player oppPlayer;
    	Player ksPlayerBestMatched=null;
    	int closestDiff=matchParams.getMaxDiff();
    	int diff;
    	
    	for (Player ksPlayer: this.players) {
            
        	if (ksPlayer.isMatchable() && ksPlayer.getBestMatch()!=null) {
        		
        		if (ksPlayer.isMustMatch()) { // If player is the mustmatch player, match as 1st
        			System.out.println("MustMatch has matched");
        			return ksPlayer;
        		}
        		
        		oppPlayer=ksPlayer.getBestMatch();
        			
        		diff=matchParams.isNinetyDay() ? Math.abs(ksPlayer.getRatingNinetyDay() - oppPlayer.getRatingNinetyDay())
							   			       : Math.abs(ksPlayer.getRating() 		    - oppPlayer.getRating());
        		
        		if (diff<=closestDiff) {
        			closestDiff=diff;
        			ksPlayerBestMatched = ksPlayer;  
        		}
        	}
    	}
    	return ksPlayerBestMatched;
    }
    
    /*
     * Clear all best matches for each player
     */
    public void clearBestMatches() {
    	
    	for (Player ksPlayer: this.players) {
            ksPlayer.setBestMatch(null);
        }	
    }

	public void addResult(String playerName, String opponent, String result) {
		
		for (Player ksplayer: this.players) {
			
			if (ksplayer.getName().equals(playerName)) {
				switch (result) {
				case "won":
					ksplayer.addWin();
					break;
				case "draw":
					ksplayer.addDraw();
					break;
				case "lost":
					ksplayer.addLoss();
					break;
				}
			}
        }	
		
	}

	public int getMatchedPlayers() {
		int count=0;
		
		for (Player ksplayer: this.players) {
			
			if (ksplayer.getBestMatch()!=null)
				count++;
		}
		
		return count;
	}
	
	public void assignRatingNinetyDay() {
		for (Player player:this.getPlayers()) {
	
	        if (!player.isPending() && player.getRatingNinetyDay()==0) {
				int rating=HTMLReader.getRatingNinetyDay(player.getName());
				player.setRatingNinetyDay(rating);
			}
			System.out.println(player.getInfo());
		}
	}
	
	public int getMaxGames() {
		int maxGames=0;
		
		for (Player ksplayer: this.players) {
			
			if (!ksplayer.isPending() && ksplayer.getActiveGames() > maxGames)
				maxGames=ksplayer.getActiveGames();
		}
		
		return maxGames;
	}
	
	public int getMinGames() {
		int minGames=99;
		
		for (Player ksplayer: this.players) {
			
			if (!ksplayer.isPending() && ksplayer.getActiveGames() < minGames)
				minGames=ksplayer.getActiveGames();
		}
		
		return minGames;
	}

	public void displayNoCloseRating() {
		
		for (Player ksplayer: this.players) {
			
			if (!ksplayer.isPending() && !ksplayer.isCloseRating())
				System.out.println(ksplayer.getInfoNoCloseRating());
		}	
	}
}
