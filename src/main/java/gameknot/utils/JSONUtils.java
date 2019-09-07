package gameknot.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import gameknot.model.Player; 

@Component
public class JSONUtils {
	
	public List<Player> getPlayersFromTableData(String tableData) {
		
		List<Player> players = new ArrayList<Player>();
		Player player = new Player();
		
		String td=extractTableData(tableData);
		
		JSONArray jsonArr = new JSONArray(td);

		for (int i = 0; i < jsonArr.length(); i++)
        {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            
            if (jsonObj.get("uid").toString().startsWith("available")) {
            	player = new Player();
            	player.setName(jsonObj.getString("name"));
            	player.setRating(jsonObj.getInt("rating"));
            	players.add(player);
            }
        }
		
		return players;
	}
	
	private String extractTableData(String js) {
		System.out.println(js);
		String td=js.replace("'<img class=img-i src=\"/img/i/plus-small-circle.png\" style=\"float: right; margin: -2px 0;\">'+uid('","\"available-")
				    .replace(":uid('", ":\"unavailable-")
				    .replace(" (co-captain)", "")
				    .replace(" (captain)", "")
				    .replace("',0)+''", "\"")
				    .replace("',1)+''", "\"")
				    .replace("',3)+''", "\"")
				    
					.replace("{t:", "{\"t\":")
				    .replace(",t:", ",\"t\":") 
					.replace("k:", "\"k\":")
					
					.replace("'<a href=\"", "\"<a href=")
					.replace("\">", ">")
					.replace("</a>'", "</a>\"")
					
					.replace("),", ")\",")
//					.replace("',", ",")
					.replace("'", "\"")

					.replace("[{","{")
					.replace("],", ",")
					.replace("},{", ",")
					.replace("[[", "[")
					.replace("]]", "]");
					
		int count = StringUtils.countMatches(td, "href");
		
		for (int i = 0;i<count;i++) {
			td=td.replaceFirst("\"t\"", "\"uid\"");
			td=td.replaceFirst("\"k\"", "\"name\"");
			td=td.replaceFirst("\"t\"", "\"rating\"");
			td=td.replaceFirst("\"t\"", "\"score\"");
			td=td.replaceFirst("\"t\"", "\"active\"");
			td=td.replaceFirst("\"k\"", "\"active2\"");
			td=td.replaceFirst("\"t\"", "\"link\"");
			td=td.replaceFirst("\"k\"", "\"finished\"");
		}
		
		System.out.println("");
		System.out.println(td);
		System.out.println("");
		return td;
	}
	
	public void convertToArray(String td) {
		
		JSONArray jsonArr = new JSONArray(td);

		for (int i = 0; i < jsonArr.length(); i++)
        {
            JSONObject jsonObj = jsonArr.getJSONObject(i);

            System.out.println(i+" " +jsonObj);
        }
		
	}
}
