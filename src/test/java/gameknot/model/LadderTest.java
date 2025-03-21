package gameknot.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gameknot.services.ProcessMockConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={ModelConfig.class, ProcessMockConfig.class})
public class LadderTest {
	
	@Autowired
	private Ladder ladder;

	@Test
	public void testGetRecentMatches() {
		
		ladder.getRecentMatches();
	}
}
