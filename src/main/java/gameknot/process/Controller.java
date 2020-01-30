package gameknot.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.model.KingSlayers;
import gameknot.model.Ladder;
import gameknot.model.MatchParameters;
import gameknot.model.OppositionTeam;
import gameknot.model.Player;
import gameknot.utils.HTMLReader;



@Component
public class Controller {
    
    @Autowired
    private Ladder ladder;
    
    @Autowired
    private MatchParameters matchParams;
    
    @Autowired
    private KingSlayers kingslayers;
	
//    @PostConstruct
	public void mainProcess() throws Exception {
		
		boolean matchHigher = true; // MatchParameters with teams above us
		boolean matchLower = false;
		int maxGames=6; // Maximum games for kingslayer
		
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
		
		for (Player player:kingslayers.getPlayers()) {
			
			if (player.getActiveGames()>maxGames)
				player.setAboveGameLimit(true);
		}	
		
//		setPlayerPending("kimcote",false);
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
		setPlayerPending("kila01",false);
//		setPlayerPending("larksnest",false);
//		setPlayerPending("lesktheglut", true);
		setPlayerPending("gavish",false);
//		setPlayerMatchable("greenlord",false);
//		setPlayerPending("olderchessplayer",false);
//		setPlayerPending("pawnish", true);
//		setPlayerStatus("rave83",false,false);
//		setPlayerMatchable("stevers", true);
//		setPlayerStatus("white_knight48", false,false);
		setPlayerPending("allanchessw", true);
		setPlayerPending("9maxmut9", true);
		
//		kingslayers.setMustMatch("evilgm");
		
//		String html=myfileUtils.myReadFile("191231.xml");
//		html+=myfileUtils.myReadFile("191223.xml");
//		html+=myfileUtils.myReadFile("191217.xml");
//		html+=myfileUtils.myReadFile("191210.xml");
//		html+=myfileUtils.myReadFile("191203.xml");
//		htmlReader.getGameHistory(html);
//		
//		System.exit(0);
		
		for (Player player:kingslayers.getPlayers()) {

	        player.setCloseRating(true);
	        
			if (player.isMatchable()) {
				int rating=HTMLReader.getRatingNinetyDay(player.getName());
				player.setRatingNinetyDay(rating);
			}
			
			System.out.println(rightpad(player.getName(),15)
					+" Matchable="		+String.format("%1$5s", player.isMatchable())
//					+" Above Game List="+player.isAboveGameLimit()
//					+" Pending="		+player.isPending()
					+" Rating="			+player.getRating()
					+" Rating 90 Day="	+player.getRatingNinetyDay()
					+" Active Matches=" +player.getActiveGames());
		}
		
		System.out.println("Kingslayers Matchable Players="+kingslayers.getMatchablePlayers());
		
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
			
			oppTeam.setWrongRank(oppTeam.getRank()>kingslayers.getRank() && !matchLower
							  || oppTeam.getRank()<kingslayers.getRank() && !matchHigher);
			
		}
		
//		setTeamPending("THE BLACK STALLION INTERNATIONAL CHESS TEAM");
//		setTeamPending("A Castle on the KING's Court # 2"); 
//		setTeamPending("~Canadian Bacon~");
//		setTeamOnly("Christ Our Redeemer"); // Capt Shirley
//		setTeamPending("DACII LIBERI");
//		setTeamPending("The Empire Strikes Back");
//		setTeamPending("The Force");
//		setTeamPending("IHS TEAM ITALIA");
//		setTeamPending("♔ Immortal Kings ♔");
//		setTeamPending("International Chess DOGS");
//		setTeamPending("~✠~ KNIGHTS TEMPLAR ~✠~"); // Capt goldaric
//		setTeamPending("Masters of the game"); // Declined no reason
//		setTeamPending("Kings of the Castle");
//		setTeamPending("The Knights of the Crystal Castle"); // Apologies but your current team rank doesn't match our criteria, but thank you for the challenge
//		setTeamPending("Knights of the Sceptered Isle");
//		setTeamPending("The Outcasts");						// We prefer closer ratings. 90 day ratings is secondary parameter.
//		setTeamPending("Peace, Love, and Bunny Rabbits");
//		setTeamOnly("Never Look Back");
//		setTeamPending("\" S.W.A.T. \"");
//		setTeamPending("Scotland Forever");
//		setTeamPending("SmAsHeDcRaBs");
//		setTeamPending("TRITON"); // Capt Neil
//		setTeamPending("Yeshua's Army");
		
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
	
			if (kingslayers.getMatchablePlayers() <2)
				break;
			
			if (oppTeam.isMatchable()) { // at this point only pending or wrong rank
				oppTeam.assignPlayers();
				
				for (Player oppPlayer: oppTeam.getPlayers()) {
					oppPlayer.assignCloseRating(kingslayers,60);
				}
				
				System.out.println("Rank=" + oppTeam.getRank() 
				+ " Team="+rightpad(oppTeam.getName(),45)
				+ " Pending Games="+oppTeam.getPendingGames()
//					+ " Active Matches="+oppTeam.getActiveMatches() 
				+ " Available Players="+oppTeam.getPlayers().size()
				+ " Pending Players="+oppTeam.getPendingPlayers()
				+ " Close Rating Players="+oppTeam.getCloseRatingPlayers()
				+ " Matchable Players="+oppTeam.getMatchablePlayers());
			}		
		}	
		
		setPlayerNotThreeDay("cloud-runner"); // The Knights of the Crystal Castle
		setPlayerNotThreeDay("dugstool");
		setPlayerNotThreeDay("unicorn88");
		setPlayerNotThreeDay("hexenhammer");
		setPlayerNotThreeDay("dirk_winwood");
		setPlayerNotThreeDay("souraj");
		setPlayerNotThreeDay("king_0_nothing");
		setPlayerNotThreeDay("qr19kaash");
		setPlayerNotThreeDay("linasto");
		setPlayerNotThreeDay("smaryam9");
		setPlayerNotThreeDay("vince2012");
		setPlayerNotThreeDay("mervynrothwell");
		setPlayerNotThreeDay("adelzaki");
		setPlayerNotThreeDay("withstand"); // The Knights of the Crystal Castle
		setPlayerNotThreeDay("rsinq"); // THE BLACK STALLION INTERNATIONAL CHESS TEAM
		setPlayerNotThreeDay("sculldog"); // The Outcasts
		/*
		 * 
		 */
//		for (int i=0;i<=maxGames;i++) {
		int i = maxGames;
			
			System.out.println("Active Games Limit="+i);
			
			for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
		
				if (oppTeam.isMatchable()) { // at this point only pending or wrong rank
				
					for (Player oppPlayer: oppTeam.getPlayers()) {				
						oppPlayer.setAboveGameLimit(oppPlayer.getActiveGames()>i);
					}
		
					System.out.println("Rank=" + oppTeam.getRank() 
						+ " Team="+rightpad(oppTeam.getName(),45)
						+ " Pending Games="+oppTeam.getPendingGames()
	//					+ " Active Matches="+oppTeam.getActiveMatches() 
						+ " Available Players="+oppTeam.getPlayers().size()
						+ " Pending Players="+oppTeam.getPendingPlayers()
						+ " Close Rating Players="+oppTeam.getCloseRatingPlayers()
						+ " Matchable Players="+oppTeam.getMatchablePlayers());
				}
			}	
		
			matchParams.run(60, true,true); // Higher 90 Day Rating - MatchParameters on 90 day

			matchParams.run(60, true,false); // Higher 90 day rating - match on normal
//		}
		
		matchParams.run(50, true,true); // Higher 90 Day Rating - MatchParameters on 90 day

		matchParams.run(50, true,false); // Higher 90 day rating - match on normal
		
//		matchParams.run(50, false,true); // any, match on 90 day
//		
//		matchParams.run(50, false,false); // any, match on normal
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
			if (oppTeam.isMatchable()) { 
				
				for (Player oppPlayer: oppTeam.getPlayers()) {
					oppPlayer.setAboveGameLimit(false);
				}
			}
		}
		
		System.out.println("Active Games Limit=any");
		
		for (OppositionTeam oppTeam: ladder.getOppositionTeams()) {	
	
			if (kingslayers.getMatchablePlayers() <2)
				break;
			
			if (oppTeam.isMatchable()) { // at this point only pending or wrong rank
				
				System.out.println("Rank=" + oppTeam.getRank() 
						+ " Team="+rightpad(oppTeam.getName(),45)
						+ " Pending Games="+oppTeam.getPendingGames()
//						+ " Active Matches="+oppTeam.getActiveMatches() 
						+ " Available Players="+oppTeam.getPlayers().size()
						+ " Pending Players="+oppTeam.getPendingPlayers()
						+ " Close Rating Players="+oppTeam.getCloseRatingPlayers()
						+ " Matchable Players="+oppTeam.getMatchablePlayers());
			}
		}	
		
		
		matchParams.run(50, true,true); // Higher 90 Day Rating - MatchParameters on 90 day

		matchParams.run(50, true,false); // Higher 90 day rating - match on normal
		
		matchParams.run(50, false,true); // any, match on 90 day		
		matchParams.run(50, false,false); // any, match on normal
		
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
