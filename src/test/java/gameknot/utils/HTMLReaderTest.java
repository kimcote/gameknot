package gameknot.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gameknot.model.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={HTMLReader.class, JSONUtils.class})
public class HTMLReaderTest {
	
	@Test
	public void getTeamPlayers() throws IOException, URISyntaxException {
		byte[] data = Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("kingslayers.html").toURI()));
		String html = new String(data);
				
		String htmlTeamTableData = HTMLReader.getTeamPlayers(html);
		List<Player> players=JSONUtils.getPlayersFromTableData(htmlTeamTableData);
		System.out.println(htmlTeamTableData);
		System.out.println(players.size());
	}
	
	@Test
	public void getHTML() throws IOException, URISyntaxException {
		
		String html = HTMLReader.getHTML("/team.pl?chess=912");
		System.out.println(html);
	}
}
