package gameknot.services;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gameknot.services.MatchService;

@Configuration
public class ProcessMockConfig {
	
	@Bean
	public MatchService getMatchService() {
		return Mockito.mock(MatchService.class);
	}
}
