
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
		
		for (Player player:kingslayers.getPlayers()) {
			player.setAboveGameLimit(player.getActiveGames()>config.getMaxGames());
		}	
		
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
			String pending = player.isPending()? " Pending="+player.getPendingGames():"";
			
			System.out.println(rightpad(player.getName(),16)
					+" Matchable="		+String.format("%1$5s", player.isMatchable())
//					+" Above Game List="+player.isAboveGameLimit()
//					+" Pending="		+player.isPending()
					+" Rating="			+player.getRating()
					+" 90Day="	+String.format("%1$4s",player.getRatingNinetyDay())
					+" Active=" +String.format("%1$2s",player.getActiveGames())
					+  pending);
		}
		
		System.out.println("Kingslayers Matchable Players="+kingslayers.getMatchablePlayers());
		
		if (kingslayers.getMatchablePlayers() <2)
			System.exit(0);
		
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
		 * Get ladder players
		 */
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
			
			if (oppTeam.isMatchable()) { // at this point only pending or wrong rank
				oppTeam.assignPlayers();
				
				for (Player oppPlayer: oppTeam.getPlayers()) {
					
					if (oppPlayer.isMatchable()) {
						oppPlayer.assignCloseRating(kingslayers,config.getMaxDiff());
					}
				}
				
				System.out.println(oppTeam.getInfo());
				
//				for (Player oppPlayer: oppTeam.getPlayers()) {
//					System.out.println(oppPlayer.getInfo());
//				} 
//				System.exit(0);
			}		
		}	

		setPlayerNotThreeDay("rsinq"); // THE BLACK STALLION INTERNATIONAL CHESS TEAM
		setPlayerNotThreeDay("bobsogood"); // The Force
		setPlayerNotThreeDay("ginquita"); // IHS TEAM ITALIA
		setPlayerNotThreeDay("feisty_pawn"); // IHS TEAM ITALIA
		setPlayerNotThreeDay("cloud-runner"); // The Knights of the Crystal Castle
		setPlayerNotThreeDay("withstand"); // The Knights of the Crystal Castle
		setPlayerNotThreeDay("dugstool");
		setPlayerNotThreeDay("unicorn88"); // Lucifers Army
		setPlayerNotThreeDay("hexenhammer"); // Lucifers Army
		setPlayerNotThreeDay("dirk_winwood"); // Lucifers Army
		setPlayerNotThreeDay("bojim"); // Never Look Back
		setPlayerNotThreeDay("souraj");
		setPlayerNotThreeDay("king_0_nothing");
		setPlayerNotThreeDay("qr19kaash");
		setPlayerNotThreeDay("linasto");
		setPlayerNotThreeDay("smaryam9");
		setPlayerNotThreeDay("vince2012");
		setPlayerNotThreeDay("mervynrothwell");
		setPlayerNotThreeDay("adelzaki");
		setPlayerNotThreeDay("sculldog"); // The Outcasts
		setPlayerNotThreeDay("makiawel"); // Triton
		setPlayerNotThreeDay("charlee"); // Triton
		setPlayerNotThreeDay("gameon007"); //Yeshua's Army
		setPlayerNotThreeDay("shawnbriscoe"); // Yeshua's Army
		setPlayerNotThreeDay("delacowboy"); // Yeshua's Army
		
		setPlayerNotThreeDay("jbbooks"); // Temp
		
//		setOppPlayerNotPending("thunder-wood");
//		setOppPlayerNotPending("shepstar");
//		setOppPlayerNotPending("jack_ryan");
		
		/*
		 * Only match with players under max Game Limit
		 */
//		for (int i=maxGames;i<=maxGames;i++) { 
			
		System.out.println("\nActive Games Limit="+config.getMaxGames());
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
	
			if (oppTeam.isMatchable()) { // at this point only pending or wrong rank or Players NotThreeDay
			
				for (Player oppPlayer: oppTeam.getPlayers()) {	
					
					if (!oppPlayer.isPending())
						oppPlayer.setAboveGameLimit(oppPlayer.getActiveGames()>config.getMaxGames());
				}
	
				System.out.println(oppTeam.getInfo());
			}
		}	
	
		matchParams.run(config.getMaxDiff(), true); // Higher 90 Day Rating - MatchParameters on 90 day
//			matchParams.run(60, true,false); // Higher 90 day rating - match on normal
//		}
		
//		matchParams.run(50, true,true); // Higher 90 Day Rating - MatchParameters on 90 day
//		matchParams.run(50, true,false); // Higher 90 day rating - match on normal
		
//		matchParams.run(50, false,true); // any, match on 90 day
//		
//		matchParams.run(50, false,false); // any, match on normal
		
		/*
		 * No Active Games Limit on opposition.
		 */
		
		System.out.println("\nActive Games Limit=any");
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
			
			if (kingslayers.getMatchablePlayers() <2)
				break;
				
			if (oppTeam.getPlayers()!=null) {
				
				for (Player oppPlayer: oppTeam.getPlayers()) {
					oppPlayer.setAboveGameLimit(false);
				}
			}
					
			if (oppTeam.isMatchable()) {		
				System.out.println(oppTeam.getInfo());
			}
		}	
		
		if (config.isMatchHigherNinetyDay())
			matchParams.run(config.getMaxDiff(), true); // Higher 90 Day Rating 
		
		if (config.isMatchLowerNinetyDay())
			matchParams.run(config.getMaxDiff(), false); // any
		
		/* 
		 * Search below Kingslayer rank
		 */
//		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
//			oppTeam.setWrongRank(oppTeam.getRank()<kingslayers.getRank());
//			
//			if (oppTeam.isMatchable()) { // at this point only pending or wrong rank
//				oppTeam.assignPlayers();
//				
//				for (Player oppPlayer: oppTeam.getPlayers()) {
//					
//					if (oppPlayer.isMatchable()) {
//						oppPlayer.assignCloseRating(kingslayers,maxDiff);
//					}
//				}
//				
//				System.out.println(oppTeam.getInfo());
//			}		
//		}
//		
//		matchParams.run(maxDiff, true,true); // Higher 90 Day Rating - MatchParameters on 90 day
//		matchParams.run(50, true,false); // Higher 90 day rating - match on normal
		
//		matchParams.run(50, false,true); // any, match on 90 day		
//		matchParams.run(50, false,false); // any, match on normal
		
		/*
		 * Match with 99 difference
		 */
//		System.out.println("\nMatch 99");
//		
//		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
//			
//			if (oppTeam.getPlayers()!=null) {
//				
//				for (Player oppPlayer: oppTeam.getPlayers()) {
//					
//					if (oppPlayer.isAvailable()) {
//						oppPlayer.assignCloseRating(kingslayers,99);
//					}
//				}
//				
//				System.out.println(oppTeam.getInfo());
//			}		
//		}	
//		
//		
//		matchParams.run(99, true,true);
//		matchParams.run(99, true,false);
//		matchParams.run(99, false,true);
//		matchParams.run(99, false,false);
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
