package gameknot.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.model.KingSlayers;
import gameknot.model.OppositionTeam;
import gameknot.model.Player;

@Component
public class HTMLReader {

	@Autowired
	private static MyFileUtils fileUtils;

	@Autowired
	private KingSlayers kingSlayers;

	public void getGameHistory(String html) throws IOException, URISyntaxException, ParseException {
		
//		String html=MyFileUtils.readFile(fileName);
				
		Document document = Jsoup.parse(html);
		
		Elements elementsTable=document.select("table");
		
		for (Element elementTable: elementsTable) {
//		System.out.println(elementsTable.size());
			Elements elementsRow = elementTable.getElementsByTag("tr");
	//		System.out.println(elementsRow);
			for (int i=0;i<elementsRow.size();i++) {
				
				if (elementsRow.get(i).hasAttr("class")) {
					Elements elementsData = elementsRow.get(i).getElementsByTag("td");
					String date=elementsData.get(0).text();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
					
					//convert String to LocalDate
					LocalDate localDate = LocalDate.parse(date, formatter);
					
					Date date1=new SimpleDateFormat("dd-MMM-yy").parse(date); 
					
				    if (localDate.getMonth()==Month.DECEMBER) {  
						String playerName = elementsData.get(1).text();
						String opponent = elementsData.get(3).text();
						String result = elementsData.get(6).text();
						String s = elementsData.get(0).text() + " " + elementsData.get(1).text() + " v "
								+ elementsData.get(3).text() + " " + elementsData.get(6).text();
						System.out.println(s);
	
						kingSlayers.addResult(playerName, opponent, result);
				    }
				}
			}
		}
		
		for (Player player:kingSlayers.getPlayers()) {
			System.out.println(player.getName() 
					+ " Wins="+player.getWins()
					+ " Draws="+player.getDraws()
					+ " Losses="+player.getLosses()
					+ " Points="+(player.getWins()-player.getLosses()));
		}
//		
//		Elements elements = document.select("script");
//		
////		System.out.println("Script elements count="+elements.size());
//		
//		for (Element element: elements) {
//			html = element.html();
//			
//			if (html.contains(tableName)) {
//				int startPos =html.indexOf(tableName)+tableName.length();
//				int endPos = html.indexOf("team_players.render_table");
//				return html.substring(startPos+3, endPos-2);
//			}
//		}
//		return "Not Found";
	}

	public static String getTeamPlayers(String html) {
		final String tableName = "team_players.table_data";

		Document document = Jsoup.parse(html);

//		Elements elementsTable=document.select("table");
//		Elements elementRank = elementsTable.get(1).getElementsByTag("td");
//				
//		for (int i=0;i<elementRank.size();i++) {
//			Element e=elementRank.get(i);
//			
//			if ("Team ladder rank:".equals(e.text())) {
//				kingslayers.setRank(Integer.parseInt(elementRank.get(i+1).text()));
//			}
//		}

		Elements elements = document.select("script");

//		System.out.println("Script elements count="+elements.size());

		for (Element element : elements) {
			html = element.html();

			if (html.contains(tableName)) {
				int startPos = html.indexOf(tableName) + tableName.length();
				int endPos = html.indexOf("team_players.render_table");
				return html.substring(startPos + 3, endPos - 2);
			}
		}
		return "Not Found";
	}

	public static List<OppositionTeam> getTeamLadder(String html) throws Exception {
		int rank = 0;
		String name;
		String link;
		List<OppositionTeam> oppositionTeams = new ArrayList<OppositionTeam>();

		Document doc = Jsoup.parse(html);
		Elements tableElements = doc.select("table");

//		System.out.println("Table Element Count="+tableElements.size());
//		System.out.println(tableElements.get(1).toString());

		doc = Jsoup.parse(tableElements.get(1).toString());
		Elements trElements = doc.select("tr");

//		System.out.println("tr Element Count="+trElements.size());
//		System.out.println("Element0="+elements.get(10).html());

		for (Element element : trElements) {

			Elements tdElements = element.getElementsByTag("td");
			Elements aElements = element.getElementsByTag("a");

			if (aElements.size() > 0) {
				name = aElements.get(0).text();
				rank = Integer.parseInt(tdElements.get(0).text().replace(".", ""));
				link = aElements.get(0).attr("href");

				oppositionTeams.add(new OppositionTeam(name, rank, link));
			}
		}

		return oppositionTeams;
	}

//	public void getPendingTeams(Ladder opposition, String pendingHTML) {
//
//		Document ladderDoc = Jsoup.parse(pendingHTML);
//		Elements tableElements = ladderDoc.select("table");
//		
////		System.out.println("Pending Table Element Count="+tableElements.size());
//		
////		System.out.println(tableElements.get(1).toString());
//		
//		Document tableDoc = Jsoup.parse(tableElements.get(1).toString());
//		Elements trElements = tableDoc.select("tr");
//		
////		System.out.println("Pending Row Element Count="+trElements.size());
//		
//		for (Element element: trElements) {
////			System.out.println(teamhtml);
//			
////			Document rowDoc = Jsoup.parse(rowhtml);
//			Elements aElements = element.select("a");
//					
////			System.out.println("Pending a Element Count="+aElements.size());
//			
//			if (aElements.size()>0) {
//				String pendingTeam=aElements.get(0).text();
//				System.out.println("Pending Team="+pendingTeam);
//				
//				for (OppositionTeam oppTeam: opposition.getOppositionTeams()) {
//					
//					if (oppTeam.getName().equals(pendingTeam)) {
//						oppTeam.setPending(true);
//					}
//				}
//			}
//		}
//	}

//	public void getTeamMatches(Ladder opposition, String html) {
//		
//		try {
//			Document doc = Jsoup.parse(html);
//			Elements tableElements = doc.select("table");	
//			
//			doc = Jsoup.parse(tableElements.get(2).toString());
//			Elements trElements = doc.select("tr");
//			
//			for (Element element: trElements) {
//				Elements aElements = element.select("a");
//				
//				if (aElements.size()>0) {
//					String activeTeam=aElements.get(0).text();
//					
//					for (OppositionTeam oppTeam: opposition.getOppositionTeams()) {
//						
//						if (oppTeam.getName().equals(activeTeam)) {
//							int i = oppTeam.getActiveMatches() + 1;
//							oppTeam.setActiveMatches(i);
//							break;
//						}
//					}
//				}
//			}	
//		} catch(IndexOutOfBoundsException e) {
//			System.out.println(e.getMessage());
//			System.out.println(html);
//		}
//	}

	public static int getRatingNinetyDay(String playerName) {

		String html = WebReader.jsoup("/mini-stats.pl?u=" + playerName);

		Document doc = Jsoup.parse(html);

		Elements trElements = doc.select("tr");

		Elements tdElements = trElements.get(1).getElementsByTag("td");

		return Integer.parseInt(tdElements.get(1).text());
	}

	public static String getHTML(String link) {

		try {
//			if (testing) {
//				System.out.println("Reading file="+fileName);
//				byte[] data = Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource(fileName).toURI()));
//				return new String(data);
//			}
//			else			
			return WebReader.jsoup(link); // works
		} catch (Exception e) {
			return "Not Found";
		}
	}

	private static String readFile(String fileName) throws IOException, URISyntaxException {

//		InputStream is =
//				  HTMLReader.class.getClassLoader().getResourceAsStream("/resource/test.txt");
		byte[] data = Files
				.readAllBytes(Paths.get(HTMLReader.class.getClass().getClassLoader().getResource(fileName).toURI()));
		return new String(data);
	}
}
