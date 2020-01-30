package gameknot.process;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.model.KingSlayers;
import gameknot.model.Ladder;
import gameknot.model.MatchParameters;
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
     * Iterate through all Ladder Teams, then for each team through all players.
     * For each team get 1st and 2nd best match kingslayer. 
     * Problem is next Opp player best but same as 2nd best.
     */
//    public void matchBothTogether(int maxDiff, boolean higherNinetyDayLowerNormal, boolean ninetyDay) throws IOException, InterruptedException {
//    	
//    	String msg=new java.util.Date().toString();
//    	
//    	msg+= " matchBothTogether ";
//    	msg+= " Max Diff="+maxDiff;
//    	msg+= (higherNinetyDayLowerNormal) ?  " Higher Ninety Day Lower Normal ":" any ";
//    	msg+= (ninetyDay) ? "MatchParameters Ninety Day" : "MatchParameters Normal";
//		
//		System.out.println(msg);
//		
//		this.maxDiff=maxDiff;
//        this.higherNinetyDayLowerNormal=higherNinetyDayLowerNormal;
//        this.ninetyDay=ninetyDay;
//        
//        for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
//        	
//        	if (kingslayers.getMatchablePlayers() <2)
//        		break;
//        	
//        	if (oppTeam.getPlayers() != null 
//            && oppTeam.isMatchable()
//            && oppTeam.getMatchablePlayers()>1) {
//        		
//        		System.out.println("Rank=" + oppTeam.getRank() 
//					+ " Team="+oppTeam.getName()
////				    + " Active Matches="+oppTeam.getActiveMatches() 
//					+ " Matchable Players="+oppTeam.getMatchablePlayers());
//        	
//        		resetMatchVariables();
//            	
//            	matchOppositionTeam(oppTeam);
//            
////            	displayMatch();
//            }
//        }
//    }
    
//    private void matchOppositionTeam(OppositionTeam oppTeam) throws IOException, InterruptedException {
//    	
//	    for (Player oppPlayer: oppTeam.getPlayers()) {
//	    	
//	    	if (oppPlayer.isMatchable() && isCloseRating(oppPlayer.getRating())) {
//	    		
//	    		if (oppPlayer.getRatingNinetyDay()==0) {
//	    			int rating=htmlReader.getRatingNinetyDay(oppPlayer.getName());
//	    			oppPlayer.setRatingNinetyDay(rating);
//	//    			System.out.println(oppPlayer.getName()
//	//    					+" Rating="+oppPlayer.getRating()
//	//    					+" 90 Day Rating="+oppPlayer.getRatingNinetyDay());
//	    		}
//	 
//	            for (Player ksPlayer: kingslayers.getPlayers()) {
//	                
//	            	if (ksPlayer.isMatchable()) {
////	            		matchSinglePlayers(oppPlayer, ksPlayer);
//	            	}
//	            }	
//	        }
//	    }
//    }
    
//    private void matchSinglePlayers(Player oppPlayer, Player ksPlayer) {
//    	int diff;
////    	System.out.println("Compare ks="+getPlayerDetails(ksPlayer)+ " Opp="+getPlayerDetails(oppPlayer));
//
//    	if ((higherNinetyDayLowerNormal 
//    	 && (ksPlayer.getRating() 		  > oppPlayer.getRating()		
//    	 || ksPlayer.getRatingNinetyDay() < oppPlayer.getRatingNinetyDay()))
//    	 || Math.abs(ksPlayer.getRatingNinetyDay() - oppPlayer.getRatingNinetyDay()) > maxDiff 
//    	 || Math.abs(ksPlayer.getRating() 		   - oppPlayer.getRating()) 		 > maxDiff)
//    		return;
//    	
//    	diff=ninetyDay ? Math.abs(ksPlayer.getRatingNinetyDay() - oppPlayer.getRatingNinetyDay())
//    				   : Math.abs(ksPlayer.getRating() 	     - oppPlayer.getRating());
//    	
////    	System.out.println("Compare ks="+ksPlayer.getName()+ " Opp="+oppPlayer.getName()+ " Diff="+diffRating);
//
//        if (diff<=minDiffRating1) { 
//
//            if (oppPlayer != matchedOppPlayer1  // Have not already matched to this opponent
//             && ksPlayer!= matchedKSPlayer1 	// Dont demote if same KS player
//             && matchedKSPlayer1!=null) {		// is not first match made for this kingslayer
//		        System.out.println("Dem MatchParameters 2 ks="+matchedKSPlayer1.getName()+ " Opp="+matchedOppPlayer1.getName()+ " Diff="+minDiffRating1);
//                minDiffRating2 = minDiffRating1;
//                matchedKSPlayer2 = matchedKSPlayer1;
//                matchedOppPlayer2 = matchedOppPlayer1;
//            }
//              
//		    System.out.println("Set MatchParameters 1 ks="+ksPlayer.getName()+ " Opp="+oppPlayer.getName()+ " Diff="+diff);
//            minDiffRating1=diff;
//            matchedKSPlayer1 = ksPlayer;  
//            matchedOppPlayer1 = oppPlayer;
//        }
//        else if (diff <= minDiffRating2 
//        	   && oppPlayer != matchedOppPlayer1
//        	   && ksPlayer  != matchedKSPlayer1)  { 
//        	
//		    System.out.println("Set MatchParameters 2 ks="+ksPlayer.getName()+ " Opp="+oppPlayer.getName()+ " Diff="+diff);
//            minDiffRating2=diff;
//            matchedKSPlayer2 = ksPlayer;
//            matchedOppPlayer2=oppPlayer;
//        }
//    }
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
    	msg+= (matchParams.isNinetyDay()) ? "MatchParameters Ninety Day" : "MatchParameters Normal";
		
		System.out.println(msg);
        
    	for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
        	
        	if (kingslayers.getMatchablePlayers() <2)
        		break;
        	
        	if (oppTeam.isMatchable()) {
	        	System.out.println("Rank="+oppTeam.getRank() 
								+ " Team="+oppTeam.getName()
								+ " Matchable Players="+oppTeam.getMatchablePlayers());
	      
        		kingslayers.clearBestMatches();		        	
	        	kingslayers.assignBestMatch(matchParams,oppTeam); // 1st
	        	
	        	matchedKSPlayer1=kingslayers.getBestMatched();
	        	
	        	if (matchedKSPlayer1!=null) {
	        	
	        		if (kingslayers.getMustMatch()==null 		// No must match player
	        	     || kingslayers.getMustMatch().equals(matchedKSPlayer1)) { // Must match player has matched 
	            	
		        		matchedKSPlayer1.setMatched(true);
		        		matchedKSPlayer1.getBestMatch().setMatched(true);
//			        		System.out.println("Set MatchParameters 1 ks="+getPlayerDetails(matchedKSPlayer1)+ " Opp="+getPlayerDetails(matchedOppPlayer1));
		        	
		        		kingslayers.assignBestMatch(matchParams,oppTeam); // 2nd
		        	
		        		matchedKSPlayer2=kingslayers.getBestMatched();
		        	
		        		if (matchedKSPlayer2!=null) {
		        			matchedKSPlayer2.setMatched(true);
		        			matchedKSPlayer2.getBestMatch().setMatched(true);
//			        			System.out.println("Set MatchParameters 2 ks="+getPlayerDetails(matchedKSPlayer2)+ " Opp="+getPlayerDetails(matchedOppPlayer2));
		        			displayMatch(matchedKSPlayer1, matchedKSPlayer2, matchedKSPlayer1.getBestMatch(), matchedKSPlayer2.getBestMatch());
		        			oppTeam.setPending(true);
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
