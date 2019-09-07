package gameknot.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class HTMLReader {

	public String getTeamPlayers(String html) throws IOException, URISyntaxException {
		final String tableName="team_players.table_data";
		
		Document document = Jsoup.parse(html);
		Elements elements = document.select("script");
		
		System.out.println(elements.size());
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
	
	public String getTeamLadder(String html) throws IOException, URISyntaxException {
		
		int startPos = html.indexOf("<table class=\"list\" width=\"750\">");
		int endPos = html.indexOf("</table",startPos)+8;
		System.out.println(startPos + " " + endPos);
		String teams=html.substring(startPos, endPos);
		
//		System.out.println(teams);
		
		final String tableName="table";
		
		Document document = Jsoup.parse(html);
		Elements elements = document.select("script");
		
		System.out.println(elements.size());
		for (Element element: elements) {
			html = element.html();
			
			if (html.contains(tableName)) {
				startPos =html.indexOf(tableName)+tableName.length();
				endPos = html.indexOf("/table");
				System.out.println(startPos + " " + endPos);
				return html.substring(startPos+3, endPos-2);
			}
		}
		return "Not found";
	}
}
