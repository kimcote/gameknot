package gameknot.process;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.model.KingSlayers;
import gameknot.model.OppositionTeam;
import gameknot.model.OppositionTeams;
import gameknot.model.Player;
import gameknot.utils.HTMLReader;

@Component
public class MatchPlayers {
	
	@Autowired
	private HTMLReader htmlReader;
	
	@Autowired
    private KingSlayers kingslayers;
	
	@Autowired
    private OppositionTeams oppositionTeams;
    
    
	
	int diffRating;
	int diffRatingNinetyDay;
    int minDiffRating1;
    int minDiffRating2;
    int minDiffRatingNinetyDay1;
    int minDiffRatingNinetyDay2;
    
    Player matchedKSPlayer1 = null;
    Player matchedKSPlayer2 = null;
    
    Player matchedOppPlayer1 = null;
    Player matchedOppPlayer2 = null;

    public void process() throws IOException, InterruptedException { 
        
        for (OppositionTeam oppTeam: oppositionTeams.getOppositionTeams()) {

        	minDiffRating1=50;
        	minDiffRating2=50;
        	minDiffRatingNinetyDay1=50;
        	minDiffRatingNinetyDay2=50;
        	
            matchedKSPlayer1 = null;
            matchedKSPlayer2 = null;
            
            matchedOppPlayer1 = null;
            matchedOppPlayer2 = null;
            
            if (oppTeam.getPlayers() != null 
             && oppTeam.getPlayers().size() > 0
             && !oppTeam.isPending()
             && oppTeam.getCountPlayersAvailable()>1) {
            	
	            for (Player oppPlayer: oppTeam.getPlayers()) {
	            	
	            	if (!oppPlayer.isPending() && !oppPlayer.isNotThreeDay()) {
	            		int rating=htmlReader.getRatingNinetyDay(oppPlayer.getName());
	            		oppPlayer.setRatingNinetyDay(rating);
	        
		                for (Player ksPlayer: kingslayers.getPlayers()) {
		                    
		                	if (!ksPlayer.isPending()) {
		                		matchSinglePlayers(oppPlayer, ksPlayer, true);
		                	}
		                }	
	                }
	            }
            
	            if (matchedKSPlayer1 != null && matchedKSPlayer2 != null) {
	            	matchedKSPlayer1.setPending(true);
	            	matchedKSPlayer2.setPending(true);
	            	
	            	System.out.println();
	                System.out.println("Team=" + oppTeam.getName() + " Active Matches="+oppTeam.getActiveMatches());
	                System.out.println(
	                		getPlayerDetails(matchedOppPlayer1)
	                		+" v "
	                		+ getPlayerDetails(matchedKSPlayer1)
	                		+" Diff=" + minDiffRating1);
	                System.out.println(
	                		getPlayerDetails(matchedOppPlayer2)
	                		+" v "
	                		+ getPlayerDetails(matchedKSPlayer2)
	                		+" Diff=" + minDiffRating1);
	                

	                System.out.println("Hi Captain");
	                if (oppTeam.getActiveMatches()==0)
	                	System.out.println("I dont think we are playing each other currently. How about");
	                System.out.println(matchedOppPlayer1.getName() + " v "+matchedKSPlayer1.getName());
	                System.out.println(matchedOppPlayer2.getName() + " v "+matchedKSPlayer2.getName());
	                System.out.println("OK?");
	                		
	                if (kingslayers.getCountPlayersAvailable()<2) break;		
	            }
            }
        }
    }
    
    private void matchSinglePlayers(Player oppPlayer, Player ksPlayer, boolean NinetyDay) {
    	
    	
    	diffRating=NinetyDay ? Math.abs(ksPlayer.getRatingNinetyDay() - oppPlayer.getRatingNinetyDay())
    						 : Math.abs(ksPlayer.getRating() 		  - oppPlayer.getRating());
        
        if (diffRating<minDiffRating1 ) {
            
        	/* Haven't already matched this opponent, am not overwriting KS players,  */
            if (oppPlayer != matchedOppPlayer1 && ksPlayer!= matchedKSPlayer1 && matchedKSPlayer1!=null) {
//		            System.out.println("Dem Match 2 ks="+matchedKSPlayer1.getName()+ " Opp="+matchedOppPlayer1.getName()+ " Diff="+minDiff1);
                minDiffRating2 = minDiffRating1;
                matchedKSPlayer2 = matchedKSPlayer1;
                matchedOppPlayer2 = matchedOppPlayer1;
            }
              
//		        System.out.println("Set Match 1 ks="+ksPlayer.getName()+ " Opp="+oppPlayer.getName()+ " Diff="+diff);
            minDiffRating1=diffRating;
            matchedKSPlayer1 = ksPlayer;  
            matchedOppPlayer1 = oppPlayer;
        }
        else if (diffRating < minDiffRating2 
        		&& oppPlayer != matchedOppPlayer2 
        		&& ksPlayer  != matchedKSPlayer2 
        		&& ksPlayer  != matchedKSPlayer1)  {
        	
//		        System.out.println("Set Match 2 ks="+ksPlayer.getName()+ " Opp="+oppPlayer.getName()+ " Diff="+diff);
            minDiffRating2=diffRating;
            matchedKSPlayer2 = ksPlayer;
            matchedOppPlayer2=oppPlayer;
        }
    }
    
    private String getPlayerDetails(Player player) {
    	
    	return player.getName()
        		+" Rating="+player.getRating()
        		+"("+ player.getRatingNinetyDay()+")";
    }
}
