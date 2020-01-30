package gameknot.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

	@Bean
	public KingSlayers getKingSlayers() {
		return new KingSlayers();
	}
	
	@Bean
	public Ladder getOpposition() {
		return new Ladder();
	}
	
	@Bean
	public MatchParameters getMatch() {
		return new MatchParameters();
	}
}
