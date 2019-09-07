package gameknot.model;

import lombok.Data;

@Data
public class Player {

    public Player(String name, int ranking) {
        super();
        this.name = name;
        this.ranking = ranking;
    }
    private String name;
    private int ranking;
    
}
