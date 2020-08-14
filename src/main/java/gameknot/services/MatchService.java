package gameknot.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.model.KingSlayers;
import gameknot.model.Ladder;
import gameknot.model.OppositionTeam;
import gameknot.model.Player;

@Component
public class MatchService {
	
	@Autowired
    private KingSlayers kingslayers;
	
	@Autowired
    private Ladder ladder;
	
	@Autowired
	private MatchParameters matchParams;

    /*
     * Iterate through all opponent teams
     */
    public void match() throws IOException, InterruptedException {
    	Player matchedKSPlayer1 = null;
        Player matchedKSPlayer2 = null;
       
    	String msg=new java.util.Date().toString();
    	
    	msg+= " matchOneAtATime ";
    	msg+= " Max Diff="+matchParams.getMaxDiff();
    	msg+= (matchParams.isHigherNinetyDayLowerNormal()) ?  " Higher Ninety Day Lower Normal ":" any ";
    	msg+= (matchParams.isNinetyDay()) ? "Match Ranking=Ninety Day" : "MatchParameters=Normal";
		
		System.out.println(msg);
//        System.out.println("ladder Teams To Match="+ladder.getOppositionTeams().size());
    	for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
        	
        	if (kingslayers.getMatchablePlayers() <2) {
//        		System.out.println("Kingslayers to match="+kingslayers.getMatchablePlayers());
        		break;
        	}
        	
        	if (oppTeam.isMatchable()) {
	        	System.out.println("\nRank="+oppTeam.getRank() 
								  + " Team="+oppTeam.getName()
								  + " Matchable Players="+oppTeam.getMatchablePlayers());
	      
        		kingslayers.clearBestMatches();		        	
	        	kingslayers.assignBestMatch(matchParams,oppTeam); // 1st
	        	
//	        	System.out.println("kingSlayers.matched="+kingslayers.getMatchedPlayers());
	        	
	        	if (kingslayers.getMatchedPlayers()>0) {
	        		for (Player ksPlayer:kingslayers.getPlayers()) {
	        			if (ksPlayer.getBestMatch()!=null) {
	        				String msgMatch=ksPlayer.getMatchInfo() + " v " + ksPlayer.getBestMatch().getMatchInfo(); 
	        				System.out.println("Match: "+msgMatch);
	        			}
	        		}
	        	}
	        	
	        	matchedKSPlayer1=kingslayers.getBestMatched();
	        	
	        	if (matchedKSPlayer1!=null) {
//	        		System.out.println("matckedKSPlayer1="+matchedKSPlayer1.getName());
//	        		System.out.println(kingslayers.getMustMatch().getName());
	        		if (kingslayers.getMustMatch()==null 		// No must match player
	        	     || kingslayers.getMustMatch().equals(matchedKSPlayer1)) { // Must match player has matched 
	            	
		        		matchedKSPlayer1.setMatched(true);
		        		matchedKSPlayer1.getBestMatch().setMatched(true);
//			        	System.out.println("Set MatchParameters 1 ks="+getPlayerDetails(matchedKSPlayer1)+ " Opp="+getPlayerDetails(matchedKSPlayer1.getBestMatch()));
		        	
		        		kingslayers.assignBestMatch(matchParams,oppTeam); // 2nd
		        	
		        		matchedKSPlayer2=kingslayers.getBestMatched();
		        	
		        		if (matchedKSPlayer2!=null) {
		        			matchedKSPlayer2.setMatched(true);
		        			matchedKSPlayer2.getBestMatch().setMatched(true);
			        		System.out.println("Opposition="+oppTeam.getName());
		        			displayMatch(matchedKSPlayer1, matchedKSPlayer2, matchedKSPlayer1.getBestMatch(), matchedKSPlayer2.getBestMatch());
		        			oppTeam.setPending(true);
		        			
		        			if (kingslayers.getMustMatch()!=null && kingslayers.getMustMatch().equals(matchedKSPlayer1)) {
		        				kingslayers.getMustMatch().setMustMatch(false);
		        				kingslayers.setMustMatch(null);
		        			}
		        		}
		        		else {
//			        			System.out.println("No 2nd match");
		        			matchedKSPlayer1.setMatched(false);
		        			matchedKSPlayer1.getBestMatch().setMatched(false);
		        		}
		        	}
	        	}
//        	} else {
//        		System.out.println("Rank="+oppTeam.getRank() 
//								+ " Team="+oppTeam.getName()
//								+ " Unmatchable Reasons="+oppTeam.getUnMatchableReasons());
        	}
    	}
    }
    
    private void displayMatch(Player matchedKSPlayer1, Player matchedKSPlayer2, Player matchedOppPlayer1, Player matchedOppPlayer2) {
        	
    	System.out.println();
        System.out.println(
        		getPlayerDetails(matchedOppPlayer1)
        		+" v "
        		+ getPlayerDetails(matchedKSPlayer1)
        		+" Diff=" + (matchedOppPlayer1.getRating()         -matchedKSPlayer1.getRating())
        		+"("      + (matchedOppPlayer1.getRatingNinetyDay()-matchedKSPlayer1.getRatingNinetyDay()) + ")");
      
        System.out.println(
        		getPlayerDetails(matchedOppPlayer2)
        		+" v "
        		+ getPlayerDetails(matchedKSPlayer2)
        		+" Diff=" + (matchedOppPlayer2.getRating()         -matchedKSPlayer2.getRating())
        		+"("      + (matchedOppPlayer2.getRatingNinetyDay()-matchedKSPlayer2.getRatingNinetyDay()) + ")");
        
        System.out.println("Hi Captain, would you like a match? ");
//            if (oppTeam.getActiveMatches()==0)
//            	System.out.println("I dont think we are playing each other currently. How about");
        System.out.println(matchedOppPlayer1.getName() + " v "+matchedKSPlayer1.getName() + " and ");
        System.out.println(matchedOppPlayer2.getName() + " v "+matchedKSPlayer2.getName());
        System.out.println("OK?");
        System.out.println("Cheers, Kimcote");
        System.out.println();
    }
    
    private String getPlayerDetails(Player player) {
    	
    	return player.getName()
        		+" Rating="+player.getRating()
        		+"("+ player.getRatingNinetyDay()+")"
        		+" Active Matches="+player.getActiveGames();
    }
}
