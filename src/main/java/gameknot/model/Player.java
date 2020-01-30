package gameknot.model;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import gameknot.utils.HTMLReader;
import lombok.Getter;
import lombok.Setter;

@Component
public class Player {
	
	public Player () {}

    public Player(String name, int rating, String games, boolean available) {
        this.name = name;
        this.rating = rating;
        this.available=available;
        
        if (StringUtils.contains(games, "+")) {
        	this.pending=true;
        	this.activeGames=Integer.parseInt(games.substring(0, 2).trim());
        	this.pendingGames=Integer.parseInt(games.substring(games.length()-1));
        } else {
        	this.pending=false;
        	this.activeGames=Integer.parseInt(games);
        	this.pendingGames=0;
        }
    }
    
    @Getter
    private String name;
    
    @Setter
    private boolean available;
    
    @Getter
    private int rating;
    
    @Getter
    private int wins;
    @Getter
    private int draws;
    @Getter
    private int losses;
    
    @Getter
    private int activeGames;
    
    @Getter 
    private int pendingGames;
    
    @Getter @Setter
    private int ratingNinetyDay;
    
    @Getter @Setter
    private boolean pending;
    
    @Setter
    private boolean aboveGameLimit;
    
    @Setter
    private boolean notThreeDay;
    
    @Getter @Setter
    private Player bestMatch;
    
    @Setter 
    private boolean matched;
    
    @Getter @Setter
	private boolean closeRating;
    
    public void assignCloseRating(KingSlayers kingslayers, int maxDiff) {
    	
		this.closeRating=false;
    	
    	for (Player ksPlayer: kingslayers.getPlayers()) {
            
        	if (ksPlayer.isMatchable()
        	&& Math.abs(ksPlayer.getRating() - rating) <= maxDiff) {
//        		System.out.println("CloseRating: ks="+ksPlayer.getRating()+ " v "+rating);
        		this.closeRating=true;
        		break;
        	}
    	}
    }
    
    @Getter @Setter
    private boolean mustMatch;
    
    public boolean isMatchable() {
    	return this.available && !this.pending && !this.aboveGameLimit && !this.notThreeDay && !this.matched && this.closeRating;
    }
    
    /*
     * Get best match for Player from opp Team
     */
    public void assignBestMatch(MatchParameters match, OppositionTeam oppTeam) throws IOException, InterruptedException {
    	Player bestMatch=null;
    	int diff, diffNormal, diff90Day;
    	
    	int closestDiff=match.getMaxDiff();
    	    	
    	for (Player oppPlayer: oppTeam.getPlayers()) {
	    	
	    	if (oppPlayer.isMatchable()) {
	    		
	    		if (oppPlayer.getRatingNinetyDay()==0) {
	    			int rating=HTMLReader.getRatingNinetyDay(oppPlayer.getName());
	    			oppPlayer.setRatingNinetyDay(rating);
	    		}
	    		
	    		diff90Day  = Math.abs(this.getRatingNinetyDay() - oppPlayer.getRatingNinetyDay());
	    		diffNormal = Math.abs(this.getRating()          - oppPlayer.getRating());
	    		diff = match.isNinetyDay() ? diff90Day : diffNormal;
	    		
	    		if (diff90Day  <= match.getMaxDiff()
	    		 && diffNormal <= match.getMaxDiff()
	    		 && diff       <= closestDiff
	    		 && (match.isHigherNinetyDayLowerNormal()
	    		 && this.getRating() 		  < oppPlayer.getRating()		
	    		 && this.getRatingNinetyDay() > oppPlayer.getRatingNinetyDay()
	    		 || !match.isHigherNinetyDayLowerNormal())) {		
		    		closestDiff=diff;
		    		bestMatch=oppPlayer;
	    		}
	    	}
    	}
    	
    	this.bestMatch=bestMatch;
    }

	public void addWin() {
		this.wins++;
		
	}

	public void addDraw() {
		this.draws++;
		
	}

	public void addLoss() {
		this.losses++;
	}
}
