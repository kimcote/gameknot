package gameknot.utils;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsMockConfig {

	@Bean
	public HTMLReader getHTMLReader() {
		return Mockito.mock(HTMLReader.class);
	}
	
	@Bean
	public JSONUtils getJSONUtils() {
		return Mockito.mock(JSONUtils.class);
	}
	
	@Bean
	public WebReader getWebReader() {
		return Mockito.mock(WebReader.class);
	}
}
