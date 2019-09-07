package gameknot.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class KingSlayers {

    private List<Player> players;
}
