package gameknot.model;

import lombok.Data;

@Data
public class Player {

    public Player(String name, int rating, String active, boolean pending, boolean notThreeDay) {
        super();
        this.name = name;
        this.rating = rating;
        this.active=active;
        this.pending=pending;
        this.notThreeDay=notThreeDay;
    }
    
    private String name;
    private int rating;
    private int ratingNinetyDay;
    private String active;
    private boolean pending;
    private boolean notThreeDay;
    
}
