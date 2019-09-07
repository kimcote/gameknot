package gameknot;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.model.KingSlayers;
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
	
	@PostConstruct
	public void mainProcess() throws Exception {
		
		KingSlayers kingslayers=new KingSlayers();
		//webReader.directFromURL("https://gameknot.com/team.pl?chess=912"); // 403 - Forbidden
		
		//String htmlTeamPage = webReader.jsoup("https://gameknot.com/team.pl?chess=912"); // works
		byte[] data = Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("team.html").toURI()));
		String htmlTeamPage = new String(data);
		
		String htmlTeamTableData = htmlReader.getTeamPlayers(htmlTeamPage);
		kingslayers.setPlayers(jsonUtils.getPlayersFromTableData(htmlTeamTableData));
		
		for (Player player:kingslayers.getPlayers()) {
//			player=kingslayers.getPlayers().;
			System.out.println(player.getName()+" "+player.getRating());
		}
		
//		jsonUtils.convertToArray(teamdata);
		
//		webReader.jsoup("https://gameknot.com/team-ladder.pl");
		
		data = Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("ladder.html").toURI()));
		String teamLadder = new String(data);
		
		teamLadder=htmlReader.getTeamLadder(teamLadder);
	}
}
