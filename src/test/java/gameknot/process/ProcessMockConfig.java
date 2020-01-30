package gameknot.process;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessMockConfig {
	
	@Bean
	public MatchService getMatchService() {
		return Mockito.mock(MatchService.class);
	}
}
