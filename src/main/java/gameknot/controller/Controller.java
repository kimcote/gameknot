package gameknot.controller;

import java.util.Comparator;

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
//			player.setCloseRating(true);
			if (player.getActiveGames()>config.getMaxGames())
				player.setAboveGameLimit(true);
		}	
		
//		setPlayerPending("abythomas1",false);
//		setPlayerPending("darren6464",true);
//		setPlayerMatchable("ehenrymay",true);
//		setPlayerPending("evilgm",false);
//		setPlayerPending("jom77",false);
//		setPlayerMatchable("jonah1938",true);
//		setPlayerPending("joelw",false);
//		setPlayerStatus("juhu", false, false);
//		setPlayerMatchable("madscan",true);
//		setPlayerMatchable("mickey156",true);
//		setPlayerPending("kila01",false);
//		setPlayerPending("kimcote",false);
//		setPlayerPending("larksnest",false);
//		setPlayerPending("lesktheglut", true);
//		setPlayerPending("gavish",false);
//		setPlayerMatchable("greenlord",false);
//		setPlayerPending("olderchessplayer",false);
//		setPlayerPending("pawnish", true);
//		setPlayerStatus("rave83",false,false);
//		setPlayerMatchable("stevers", true);
//		setPlayerPending("thatcha", false);
//		setPlayerStatus("white_knight48", false,false);
//		setPlayerPending("allanchessw", true);
		setPlayerPending("9maxmut9", true);
		
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
		
//		kingslayers.setRank(20);
		
		System.out.println("Kingslayers Rank="+kingslayers.getRank());
		/*
		 * Set matchable of opposition dependent on rank 
		 */
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {
			
			oppTeam.setWrongRank(oppTeam.getRank()>kingslayers.getRank() && !config.isMatchLower()
							  || oppTeam.getRank()<kingslayers.getRank() && !config.isMatchHigher());
			
		}
		
//		setTeamPending("THE BLACK STALLION INTERNATIONAL CHESS TEAM");
//		setTeamPending("A Castle on the KING's Court # 2"); 
		setTeamPending("~Canadian Bacon~");
//		setTeamOnly("Christ Our Redeemer"); // Capt Shirley
//		setTeamPending("DACII LIBERI");
//		setTeamPending("The Empire Strikes Back");
//		setTeamPending("The Force");
//		setTeamPending("IHS TEAM ITALIA");
		setTeamPending("♔ Immortal Kings ♔");
//		setTeamPending("International Chess DOGS");
		setTeamPending("LUCIFER'S ARMY");
		setTeamPending("~✠~ KNIGHTS TEMPLAR ~✠~"); // Capt goldaric
//		setTeamPending("Masters of the game"); // Declined no reason
		setTeamPending("Kings of the Castle");
//		setTeamPending("The Knights of the Crystal Castle"); // Apologies but your current team rank doesn't match our criteria, but thank you for the challenge
		setTeamPending("Knights of the Sceptered Isle");	// Checks 90 day
//		setTeamPending("The Outcasts");						// We prefer closer ratings. 90 day ratings is secondary parameter.
		setTeamPending("Peace, Love, and Bunny Rabbits");
//		setTeamOnly("Never Look Back");
//		setTeamPending("\" S.W.A.T. \"");
//		setTeamPending("Scotland Forever");
//		setTeamPending("SmAsHeDcRaBs");
//		setTeamPending("TRITON"); // Capt Neil
		setTeamPending("Yeshua's Army");
		
//		setTeamOnly("~Canadian Bacon~");
//		setTeamOnly("Conexão Macaxeira");
//		setTeamOnly("~✠~THE CRUSADERS ~✠~ International Chess Team");
//		setTeamOnly("▄▀|GLOBAL BOUNTY HUNTERS|▄▀"); // doesnt play 3 day matches
//		setTeamOnly("THE BARMY ARMY");
//		Kings of the Castle

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
		setPlayerNotThreeDay("cloud-runner"); // The Knights of the Crystal Castle
		setPlayerNotThreeDay("withstand"); // The Knights of the Crystal Castle
		setPlayerNotThreeDay("dugstool");
		setPlayerNotThreeDay("unicorn88"); // Lucifers Army
		setPlayerNotThreeDay("hexenhammer"); // Lucifers Army
		setPlayerNotThreeDay("dirk_winwood"); // Lucifers Army
		setPlayerNotThreeDay("souraj");
		setPlayerNotThreeDay("king_0_nothing");
		setPlayerNotThreeDay("qr19kaash");
		setPlayerNotThreeDay("linasto");
		setPlayerNotThreeDay("smaryam9");
		setPlayerNotThreeDay("vince2012");
		setPlayerNotThreeDay("mervynrothwell");
		setPlayerNotThreeDay("adelzaki");
		setPlayerNotThreeDay("sculldog"); // The Outcasts
		setPlayerNotThreeDay("gameon007"); //Yeshua's Army
		setPlayerNotThreeDay("shawnbriscoe"); // Yeshua's Army
		setPlayerNotThreeDay("delacowboy"); // Yeshua's Army
		
		setPlayerNotThreeDay("jbbooks"); // Temp
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
//		
//		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
//				
//			if (oppTeam.getPlayers()!=null) {
//				
//				for (Player oppPlayer: oppTeam.getPlayers()) {
//					oppPlayer.setAboveGameLimit(false);
//				}
//			}
//		}
		
//		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
//	
//			if (kingslayers.getMatchablePlayers() <2)
//				break;
//			
//			if (oppTeam.isMatchable()) { // at this point only pending or wrong rank
//				
//				System.out.println(oppTeam.getInfo());
//			}
//		}	
		
		
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
		setPlayerStatus(playerName, pending,false);
	}
	private void setPlayerStatus(String playerName, boolean pending, boolean aboveLimit) {
		
		for (Player player:kingslayers.getPlayers()) {
			
			if (player.getName().equals(playerName)) {
				player.setPending(pending);
				player.setAboveGameLimit(aboveLimit);
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
	
	private String rightpad(String text, int length) {
	    return String.format("%-" + length + "." + length + "s", text);
	}
}
