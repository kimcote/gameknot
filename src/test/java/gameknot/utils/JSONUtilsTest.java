package gameknot.utils;

import static org.junit.Assert.assertEquals;

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
@ContextConfiguration(classes={UtilsConfig.class})
public class JSONUtilsTest {
	
	@Test
	public void getPlayersFromTableData () throws IOException, URISyntaxException {
		
		//byte[] data = Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("kingslayersTableData.json").toURI()));
		String s = new String ( Files.readAllBytes( Paths.get(this.getClass().getClassLoader().getResource("kingslayersTableData.json").toURI())) );
//		Mockito.when(HTMLReader.getHTML(Mockito.anyString())).thenReturn("");
//		Mockito.when(HTMLReader.getTeamPlayers(Mockito.anyString())).thenReturn(data.toString());
//		System.out.println(s);
		List<Player> players = JSONUtils.getPlayersFromTableData(s);
		
		assertEquals(13, players.size());
	}
}
