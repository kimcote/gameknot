package gameknot.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {

	@Bean
	public HTMLReader getHTMLReader() {
		return new HTMLReader();
	}
	
	@Bean
	public JSONUtils getJSONUtils() {
		return new JSONUtils();
	}
	
	@Bean
	public WebReader getWebReader() {
		return new WebReader();
	}
}
