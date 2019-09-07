package gameknot.process;

import org.springframework.stereotype.Component;

import gameknot.model.KingSlayers;
import gameknot.model.OppositionTeam;
import gameknot.model.OppositionTeams;
import gameknot.model.Player;

@Component
public class MatchPlayers {

    public void process(KingSlayers kingslayerPlayers, OppositionTeams oppositionTeams) {
        
        int diff=99;
        int minDiff1=99;
        int minDiff2=99;
        
        Player matchedKSPlayer1 = null;
        Player matchedKSPlayer2 = null;
        
        Player matchedOppPlayer1 = null;
        Player matchedOppPlayer2 = null;
        
        for (OppositionTeam oppTeam: oppositionTeams.getOppositionTeams()) {

            minDiff1=99;
            minDiff2=99;
            
            for (Player oppPlayer: oppTeam.getPlayers()) {
        
                for (Player ksPlayer: kingslayerPlayers.getPlayers()) {
                    
                    diff=Math.abs(ksPlayer.getRanking() - oppPlayer.getRanking());
                    
                    if (diff<minDiff1) {
                        
                        if (oppPlayer != matchedOppPlayer1) {
                            minDiff2 = minDiff1;
                            matchedKSPlayer2 = matchedKSPlayer1;
                            matchedOppPlayer2 = matchedOppPlayer1;
                        }
                                       
                        minDiff1=diff;
                        matchedKSPlayer1 = ksPlayer;  
                        matchedOppPlayer1 = oppPlayer;
                    }
                    else if (diff < minDiff2 && oppPlayer != matchedOppPlayer1) {
                        minDiff2=diff;
                        matchedKSPlayer2 = ksPlayer;
                        matchedOppPlayer2=oppPlayer;
                    }
                }
            }
            
            if (matchedKSPlayer1 != null && matchedKSPlayer2 != null) {
                System.out.println("Team=" + oppTeam.getName());
                System.out.println(matchedOppPlayer1.getName()+"("+matchedOppPlayer1.getRanking()+") v "+matchedKSPlayer1.getName()+"("+matchedKSPlayer1.getRanking()+")");
                System.out.println(matchedOppPlayer2.getName()+"("+matchedOppPlayer2.getRanking()+") v "+matchedKSPlayer2.getName()+"("+matchedKSPlayer2.getRanking()+")");
                System.out.println();
            }
        }
    }
}
