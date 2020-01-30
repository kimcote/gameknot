package gameknot.factory;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import gameknot.model.Player;

@Component
public class PlayerFactory extends AbstractFactoryBean<Player> {

	public PlayerFactory() {
		setSingleton(false);
	}

	@Override
	public Class<?> getObjectType() {
		return Player.class;
	}

	@Override
	protected Player createInstance() {
		return new Player();
	}
}
