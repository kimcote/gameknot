package gameknot.utils;

import static org.junit.Assert.assertEquals;

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
public class WebReaderTest {

	@Test
	public void jsoup() {
		String html=WebReader.jsoup("/team.pl?chess=912");
//		System.out.println(html);
		
		String tableData=HTMLReader.getTeamPlayers(html);
		System.out.println("Start="+tableData);
		
		List<Player> players = JSONUtils.getPlayersFromTableData(tableData);
		
		assertEquals(17, players.size());
	}
}
