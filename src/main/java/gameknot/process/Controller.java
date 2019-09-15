package gameknot.process;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.model.KingSlayers;
import gameknot.model.OppositionTeam;
import gameknot.model.OppositionTeams;
import gameknot.model.Player;
import gameknot.utils.HTMLReader;
import gameknot.utils.JSONUtils;
import gameknot.utils.WebReader;

@Component
public class Controller {

	@Autowired
	private WebReader webReader;
	
	@Autowired
	private HTMLReader htmlReader;
	
	@Autowired
	private JSONUtils jsonUtils;
    
    @Autowired
    private OppositionTeams oppositionTeams;
    
    @Autowired
    private MatchPlayers matchplayers;
    
    @Autowired
    private KingSlayers kingslayers;
	
//	@PostConstruct
	public void mainProcess() throws Exception {
		int i=0;
		String html;
		boolean testing=false;
		List <Player> players=null;
		
		//webReader.directFromURL("https://gameknot.com/team.pl?chess=912"); // 403 - Forbidden
		
		html= getHTML("/team.pl?chess=912","kingslayers.html",testing);
		String htmlTeamTableData = htmlReader.getTeamPlayers(html);
		kingslayers.setPlayers(jsonUtils.getPlayersFromTableData(htmlTeamTableData));
		
		setPlayerActive("kimcote");
		
		for (Player player:kingslayers.getPlayers()) {
			
			if (!player.isPending()) {
				int rating=htmlReader.getRatingNinetyDay(player.getName());
				player.setRatingNinetyDay(rating);
			}
			
			System.out.println(rightpad(player.getName(),20)
					+" Rating="			+player.getRating()
					+" Rating 90 Day="	+player.getRatingNinetyDay()
					+" Pending="		+player.isPending()
					+" Active Matches=" +player.getActive());
		}
		
		/*
		 * Get opposition teams
		 */
		html=getHTML("/team-ladder.pl","ladder.html",testing);
		oppositionTeams.setOppositionTeams(htmlReader.getTeamLadder(html));
		
		System.out.println("Opposition Team Count="+oppositionTeams.getOppositionTeams().size());
		
//		html=getHTML("/team_ch_list.pl","pending.html",testing);
//		htmlReader.getPendingTeams(oppositionTeams, html);
		
		setTeamPending("Peace, Love, and Bunny Rabbits");
		setTeamPending("A Castle on the KING's Court # 2");
//		setTeamPending("~Canadian Bacon~");
		setTeamPending("The Empire Strikes Back");
//		setTeamPending("The Force");
//		setTeamPending("~✠~ KNIGHTS TEMPLAR ~✠~");
//		setTeamPending("Kings of the Castle");
//		setTeamPending("The Knights of the Crystal Castle");
		setTeamPending("\" S.W.A.T. \"");
//		setTeamPending("TRITON");
		setTeamPending("Yeshua's Army");
		
		/*
		 * Get current matches
		 */
		
		for (i=0;i<3;i++) {
			html=getHTML("/team-matches.pl?team=912&active=1&finished=0&cancelled=0&pp="+i,"team-matches"+i+".html",true);
			
			if (html.contentEquals("Not Found")) {
				break;
			}
			htmlReader.getTeamMatches(oppositionTeams, html);
		}	
		/*
		 * Get opposition players
		 */
		for (OppositionTeam oppTeam: oppositionTeams.getOppositionTeams()) {	
			int rank = oppTeam.getRank();
			
			if (rank>15 || kingslayers.getCountPlayersAvailable() <2 
				|| (testing && rank>2))
				break;
			
			if (oppTeam.isPending() || oppTeam.getActiveMatches()>3) {
				System.out.println("Rank=" + rank 
						+ " Team="+oppTeam.getName()
						+ " Active Matches="+oppTeam.getActiveMatches() 
						+ " Pending");
			}
			else {
				String fileName="oppTeam"+rank+".html";
				html = getHTML(oppTeam.getLink(),fileName,testing);
				htmlTeamTableData = htmlReader.getTeamPlayers(html);
				players=jsonUtils.getPlayersFromTableData(htmlTeamTableData);
				oppositionTeams.getOppositionTeams().get(i).setPlayers(players);
			
				System.out.println("Rank=" + rank 
						+ " Team="+oppTeam.getName()
						+ " Active Matches="+oppTeam.getActiveMatches() 
						+ " Available Players="+players.size());
			}
			i++;
		}	
		
		setPlayerNotThreeDay("cloud-runner");
		
		matchplayers.process();
			
	}
	
	private String getHTML(String link, String fileName, boolean testing) throws IOException, URISyntaxException, InterruptedException {

		try {
			if (testing) {
				System.out.println("Reading file="+fileName);
				byte[] data = Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource(fileName).toURI()));
				return new String(data);
			}
			else			
				return webReader.jsoup(link); // works
		}
		catch (Exception e) {
			return "Not Found";
		}	
	}
	
	private void setTeamPending(String pendingTeam) {
		
		for (OppositionTeam oppTeam: oppositionTeams.getOppositionTeams()) {
			
			if (oppTeam.getName().equals(pendingTeam)) {
				oppTeam.setPending(true);
			}
		}
	}
	
	private void setPlayerActive (String playerName) {
		
		for (Player player:kingslayers.getPlayers()) {
			
			if (player.getName().equals(playerName)) {
				player.setPending(false);
			}
		}
	}
	
	private void setPlayerNotThreeDay(String playerName) {
		
		for (OppositionTeam oppTeam: oppositionTeams.getOppositionTeams()) {
			
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
