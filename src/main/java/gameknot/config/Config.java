package gameknot.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class Config {

	private boolean matchHigher ;
	private boolean matchLower;
	private int kingslayerRank =0;
	private boolean matchHigherNinetyDay;
	private boolean matchLowerNinetyDay;
	private int maxDiff;
	private String mustMatch;
	private List<String> playerPending;
	private List<String> playerNotPending;
	private String teamOnly;
	private List<String> teamPending;
}
