package gameknot.model;

import java.util.List;

import org.springframework.stereotype.Component;

import gameknot.utils.HTMLReader;
import lombok.Getter;

@Component
public class Ladder {
	
	@Getter
    private List<OppositionTeam> oppositionTeams;
    
    public void setOppositionTeams() throws Exception {
    	String html=HTMLReader.getHTML("/team-ladder.pl");
    	this.oppositionTeams=HTMLReader.getTeamLadder(html);
    }
    
    public int getCountMatchable() {
    	int count =0;
    	
    	for (OppositionTeam oppTeam : this.oppositionTeams) {
    		if (oppTeam.isMatchable())
    			count++;
    	}
    	
    	return count;
    }
    
    
}
