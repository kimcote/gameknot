package gameknot.model;

import java.util.List;

import lombok.Data;

@Data
public class OppositionTeam {

    public OppositionTeam(String name, List<Player> players) {
        super();
        this.name = name;
        this.players = players;
    }
    private String name;
    private List<Player> players;
}
