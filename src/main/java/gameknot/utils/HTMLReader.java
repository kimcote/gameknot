package gameknot.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.model.OppositionTeam;
import gameknot.model.OppositionTeams;

@Component
public class HTMLReader {
	
	@Autowired
	private WebReader webReader;

	public String getTeamPlayers(String html) throws IOException, URISyntaxException {
		final String tableName="team_players.table_data";
		
		Document document = Jsoup.parse(html);
		Elements elements = document.select("script");
		
//		System.out.println("Script elements count="+elements.size());
		
		for (Element element: elements) {
			html = element.html();
			
			if (html.contains(tableName)) {
				int startPos =html.indexOf(tableName)+tableName.length();
				int endPos = html.indexOf("team_players.render_table");
				return html.substring(startPos+3, endPos-2);
			}
		}
		return "Not Found";
	}
	
	public List<OppositionTeam> getTeamLadder(String html) throws IOException, URISyntaxException {
		
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();
		OppositionTeam team=null;
		
		Document doc = Jsoup.parse(html);
		Elements tableElements = doc.select("table");
		
//		System.out.println("Table Element Count="+tableElements.size());
		
//		System.out.println(tableElements.get(1).toString());
		
		doc = Jsoup.parse(tableElements.get(1).toString());
		Elements trElements = doc.select("tr");
		
//		System.out.println("tr Element Count="+trElements.size());
//		System.out.println("Element0="+elements.get(10).html());
		for (Element element: trElements) {
			
			Elements tdElements=element.getElementsByTag("td");
			Elements aElements =element.getElementsByTag("a");
			
			if (aElements.size()>0) {
				team=new OppositionTeam();
				
				int rank=Integer.parseInt(tdElements.get(0).text().replace(".", ""));
				team.setRank(rank);
				team.setName(aElements.get(0).text());
				team.setLink(aElements.get(0).attr("href"));
				oppositionTeams.add(team);
			}
		}
		
		return oppositionTeams;
	}
	
	public void getPendingTeams(OppositionTeams oppositionTeams, String pendingHTML) {

		Document ladderDoc = Jsoup.parse(pendingHTML);
		Elements tableElements = ladderDoc.select("table");
		
//		System.out.println("Pending Table Element Count="+tableElements.size());
		
//		System.out.println(tableElements.get(1).toString());
		
		Document tableDoc = Jsoup.parse(tableElements.get(1).toString());
		Elements trElements = tableDoc.select("tr");
		
//		System.out.println("Pending Row Element Count="+trElements.size());
		
		for (Element element: trElements) {
//			System.out.println(teamhtml);
			
//			Document rowDoc = Jsoup.parse(rowhtml);
			Elements aElements = element.select("a");
					
//			System.out.println("Pending a Element Count="+aElements.size());
			
			if (aElements.size()>0) {
				String pendingTeam=aElements.get(0).text();
				System.out.println("Pending Team="+pendingTeam);
				
				for (OppositionTeam oppTeam: oppositionTeams.getOppositionTeams()) {
					
					if (oppTeam.getName().equals(pendingTeam)) {
						oppTeam.setPending(true);
					}
				}
			}
		}
	}
	
	public void getTeamMatches(OppositionTeams oppositionTeams, String html) {
		
		try {
			Document doc = Jsoup.parse(html);
			Elements tableElements = doc.select("table");	
			
			doc = Jsoup.parse(tableElements.get(2).toString());
			Elements trElements = doc.select("tr");
			
			for (Element element: trElements) {
				Elements aElements = element.select("a");
				
				if (aElements.size()>0) {
					String activeTeam=aElements.get(0).text();
					
					for (OppositionTeam oppTeam: oppositionTeams.getOppositionTeams()) {
						
						if (oppTeam.getName().equals(activeTeam)) {
							int i = oppTeam.getActiveMatches() + 1;
							oppTeam.setActiveMatches(i);
							break;
						}
					}
				}
			}	
		} catch(IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			System.out.println(html);
		}
	}
	
	public int getRatingNinetyDay(String playerName) throws IOException, InterruptedException {
		
		String html=webReader.jsoup("/mini-stats.pl?u="+playerName);
		
		Document doc = Jsoup.parse(html);
		
		Elements trElements = doc.select("tr");
		
		Elements tdElements=trElements.get(1).getElementsByTag("td");
		
		return Integer.parseInt(tdElements.get(1).text());
	}
}
