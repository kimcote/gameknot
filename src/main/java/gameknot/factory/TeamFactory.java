package gameknot.factory;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import gameknot.model.Team;

@Component
public class TeamFactory extends AbstractFactoryBean<Team> {

	public TeamFactory() {
		setSingleton(false);
	}

	@Override
	public Class<?> getObjectType() {
		return Team.class;
	}

	@Override
	public Team createInstance() {
		return new Team();
	}
}
