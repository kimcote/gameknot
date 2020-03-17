package gameknot.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gameknot.services.MatchParameters;

@Configuration
public class ModelConfig {

	@Bean
	public KingSlayers getKingSlayers() {
		return new KingSlayers();
	}
	
	@Bean
	public OppositionTeam getOppositionTeam() {
		return new OppositionTeam();
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
