//package gameknot.factory;
//
//import org.springframework.beans.factory.config.AbstractFactoryBean;
//import org.springframework.stereotype.Component;
//
//import gameknot.model.OppositionTeam;
//
//@Component
//public class OppositionTeamFactory extends AbstractFactoryBean<OppositionTeam> {
//
//	public OppositionTeamFactory() {
//		setSingleton(false);
//	}
//
//	@Override
//	public Class<OppositionTeam> getObjectType() {
//		return OppositionTeam.class;
//	}
//
//	@Override
//	public OppositionTeam createInstance() {
//		return new OppositionTeam();
//	}
//}
