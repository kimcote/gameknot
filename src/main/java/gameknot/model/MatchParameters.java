package gameknot.model;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gameknot.process.MatchService;
import lombok.Data;

@Data
@Component
public class MatchParameters {
	
	@Autowired
	private MatchService matchService;

	private int maxDiff;
	private boolean higherNinetyDayLowerNormal;
	private boolean ninetyDay;
	
	public void run(int maxDiff, boolean higherNinetyDayLowerNormal, boolean ninetyDay) throws IOException, InterruptedException {
		this.maxDiff=maxDiff;
		this.higherNinetyDayLowerNormal=higherNinetyDayLowerNormal;
		this.ninetyDay=ninetyDay;
		
		matchService.match();
	}
}
