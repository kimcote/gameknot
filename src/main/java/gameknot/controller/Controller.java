
package gameknot.controller;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.config.Config;
import gameknot.model.KingSlayers;
import gameknot.model.Ladder;
import gameknot.model.OppositionTeam;
import gameknot.model.Player;
import gameknot.services.MatchParameters;
import gameknot.utils.HTMLReader;
import gameknot.utils.MyFileUtils;

@Component
public class Controller {
    
    @Autowired
    private Ladder ladder;
    
    @Autowired
    private MatchParameters matchParams;
    
    @Autowired
    private KingSlayers kingslayers;
    
    @Autowired
	private Config config;
    
    @Autowired
    private MyFileUtils myfileUtils;
    
    @Autowired
    private HTMLReader htmlReader;
	
//    @PostConstruct
	public void mainProcess() throws Exception {
		
//		List<String> files=myfileUtils.getResourceFiles(System.getProperty("java.class.path", "."));
//		
//		for (String s : files) {
//			System.out.println(s);
//		}
//		InputStream is = Controller.class.getResourceAsStream("../../resources/191231_table.xml");
//		
//		URI uri=Controller.class.getClass().getClassLoader().getResource("../resources/191231_table.xml").toURI();
		
		
//		html=webReader.getUrlContents("https://gameknot.com/team-matches.pl?team=912&active=1&finished=0&cancelled=0");
//		html=getHTML("/team-matches.pl?team=912&active=1&finished=0&cancelled=0","",false);
//		System.out.println(html);
		
		//webReader.directFromURL("https://gameknot.com/team.pl?chess=912"); // 403 - Forbidden
		kingslayers.assignPlayers();
		kingslayers.getPlayers().sort(Comparator.comparing(Player::getName));	
		
		for (String playerName: config.getPlayerPending()) {
			setPlayerPending(playerName, true);
		}
		
		for (String playerName: config.getPlayerNotPending()) {
			setPlayerPending(playerName, false);
		}
		
		kingslayers.setMustMatch(config.getMustMatch());
		
//		String html=myfileUtils.myReadFile("200205.xml");
//		html+=myfileUtils.myReadFile("200212.xml");
//		html+=myfileUtils.myReadFile("200219.xml");
//		html+=myfileUtils.myReadFile("200227.xml");
//		html+=myfileUtils.myReadFile("200306.xml");
//		htmlReader.getGameHistory(html,Month.FEBRUARY);
//		System.exit(0);
		
		for (Player player:kingslayers.getPlayers()) {

	        if (player.isMatchable()) {
				int rating=HTMLReader.getRatingNinetyDay(player.getName());
				player.setRatingNinetyDay(rating);
			}
			System.out.println(player.getInfo());
		}
		
		/*
		 * Get ladder teams
		 */
		ladder.setOppositionTeams();

//		html=getHTML("/team_ch_list.pl");
//		htmlReader.getPendingTeams(oppositionTeams, html);
		/*
		 * Get Kingslayer Rank and remove kingslayers from list
		 */
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
			
			if ("Kingslayers".equals(oppTeam.getName())) {
				kingslayers.setRank(oppTeam.getRank());
				ladder.getOppositionTeams().remove(oppTeam);
				break;
			}	
		}
		/*
		 * Get teams with whom match has started within 3 days
		 */
//		ladder.getRecentMatches();
		
		if (config.getKingslayerRank()!=0)
			kingslayers.setRank(config.getKingslayerRank());
		
		System.out.println("Kingslayers Rank="+kingslayers.getRank());
		/*
		 * If matching only to one team, set the rest to pending.
		 */
		if (!StringUtils.isEmpty(config.getTeamOnly())) {
			setTeamOnly(config.getTeamOnly());
		}
				
		/*
		 * Set matchable of opposition dependent on rank 
		 */
		else {
			for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
				
				oppTeam.setWrongRank(oppTeam.getRank()>kingslayers.getRank() && !config.isMatchLower()
								  || oppTeam.getRank()<kingslayers.getRank() && !config.isMatchHigher());
				
			}
			
			for (String teamName: config.getTeamPending()) {
				setTeamPending(teamName);
			}
		}

		System.out.println("Ladder Team Count="+ladder.getOppositionTeams().size());
		System.out.println("Ladder Teams Matchable="+ladder.getCountMatchable());
//		System.exit(0);
		/*
		 * Get current matches
		 */
		
//		for (i=0;i<3;i++) {
//			html=getHTML("//show_tgames.pl?id=912","team-matches"+i+".html",true);
//			
//			if (html.contentEquals("Not Found")) {
//				break;
//			}
//			htmlReader.getTeamMatches(oppositionTeams, html);
//		}	
		/*
		 * Get opposition players
		 */
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
			
			if (oppTeam.isMatchable()) { // at this point only pending or wrong rank
				oppTeam.assignPlayers();
				
				for (Player oppPlayer: oppTeam.getPlayers()) {
					
					if (oppPlayer.isMatchable()) {
						oppPlayer.assignCloseRating(kingslayers,config.getMaxDiff());
					}
				}
				
				System.out.println(oppTeam.getInfoPlayers());
				
//				for (Player oppPlayer: oppTeam.getPlayers()) {
//					System.out.println(oppPlayer.getInfo());
//				} 
//				System.exit(0);
			}		
		}	
		
		/*
		 * Only match with players at Game Limit and Higher 90 day rating 
		 */
		for (int games=0;games<=config.getMaxGames();games++) {
			
			System.out.println("\nActive Games Limit="+games);
			
			for (Player player:kingslayers.getPlayers()) {
				player.setAboveGameLimit(player.getActiveGames()>games);
			}	
			
			System.out.println("Kingslayers Matchable Players="+kingslayers.getMatchablePlayers());
		
			if (kingslayers.getMatchablePlayers()>=2) {
				
				for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
			
					if (oppTeam.isMatchable()) { // too many pending games or wrong rank
					
						for (Player oppPlayer: oppTeam.getPlayers()) {	
							
							if (!oppPlayer.isPending())
								oppPlayer.setAboveGameLimit(oppPlayer.getActiveGames()>games);
						}
			
						System.out.println(oppTeam.getInfoGameLimit());
					}
				}	
		
				matchParams.run(config.getMaxDiff(), true); // Higher 90 Day Rating - MatchParameters on 90 day
			}
		}
		/*
		 * Check against all players
		 */
		System.out.println("\nActive Games Limit=no Limit");

		setAllPlayersWithinGameLimit();
		
		matchParams.run(config.getMaxDiff(), true); // Higher 90 Day Rating - MatchParameters on 90 day
		/*
		 * Only match with all players any rating within 50 
		 */
		for (int games=0;games<=config.getMaxGames();games++) {
			
			System.out.println("\nActive Games Limit="+games);
			
			for (Player player:kingslayers.getPlayers()) {
				player.setAboveGameLimit(player.getActiveGames()>games);
			}	
			
			System.out.println("Kingslayers Matchable Players="+kingslayers.getMatchablePlayers());
		
			if (kingslayers.getMatchablePlayers()>=2) {
				
				for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
			
					if (oppTeam.isMatchable()) { // at this point only pending or wrong rank or Players NotThreeDay
					
						for (Player oppPlayer: oppTeam.getPlayers()) {	
							
							if (!oppPlayer.isPending())
								oppPlayer.setAboveGameLimit(oppPlayer.getActiveGames()>games);
						}
			
						System.out.println(oppTeam.getInfoGameLimit());
					}
				}	
				
				matchParams.run(config.getMaxDiff(), false); // any
			}
		}		
		/*
		 * Set all players
		 */
		System.out.println("\nActive Games Limit=no Limit");
		
		setAllPlayersWithinGameLimit();
		
		matchParams.run(config.getMaxDiff(), false); // any
	}
	
	private void setAllPlayersWithinGameLimit() {
		for (Player player:kingslayers.getPlayers()) {
			player.setAboveGameLimit(false);
		}
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
			
			if (oppTeam.isMatchable()) { // too many pending games or wrong rank
			
				for (Player oppPlayer: oppTeam.getPlayers()) {	
					
					if (!oppPlayer.isPending())
						oppPlayer.setAboveGameLimit(false);
				}
	
//				System.out.println(oppTeam.getInfoGameLimit());
			}
		}
	}
	
	private void setTeamPending(String pendingTeam) {
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
			
			if (oppTeam.getName().contains(pendingTeam)) {
				oppTeam.setPending(true);
			}
		}
	}
	
	private void setTeamOnly(String onlyTeam) {
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
			
			if (!oppTeam.getName().equals(onlyTeam)) {
				oppTeam.setPending(true);
			}
		}
	}
	private void setPlayerPending(String playerName, boolean pending) {
		
		for (Player player:kingslayers.getPlayers()) {
			
			if (player.getName().equals(playerName)) {
				player.setPending(pending);
			}
		}
	}
	
	private void setPlayerNotThreeDay(String playerName) {
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
			
			if (oppTeam.getPlayers()!=null) {
				
				for (Player oppPlayer: oppTeam.getPlayers()) {
					if (oppPlayer.getName().equals(playerName)) {
						oppPlayer.setNotThreeDay(true);
					}
				}	
			}
		}
	}
	
	private void setOppPlayerNotPending(String playerName) {
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
			
			if (oppTeam.getPlayers()!=null) {
				
				for (Player oppPlayer: oppTeam.getPlayers()) {
					if (oppPlayer.getName().equals(playerName)) {
						oppPlayer.setPending(false);
					}
				}	
			}
		}
	}
	
	private String rightpad(String text, int length) {
	    return String.format("%-" + length + "." + length + "s", text);
	}
}
