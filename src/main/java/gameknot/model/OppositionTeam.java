package gameknot.model;

import java.util.List;

import lombok.Data;

@Data
public class OppositionTeam {

	private String name;
	private List<Player> players;
}
