package br.com.meuposto.dao.impl.postgres;

import org.apache.log4j.Logger;

import br.com.meuposto.dao.FabricaDAO;

final class PostgresFabricaDAO extends FabricaDAO {

	private static Logger log = Logger.getLogger(PostgresFabricaDAO.class);
	static PostgresFabricaDAO instance;

	static {
		log.info("Instanciando e registrando como f�brica principal.");
		instance = new PostgresFabricaDAO();
		instance.registerAsMain();
	}

	private static PostgresGenericDAOImplBO postgresGenericDAOImplBO;
	private static PostgresPostoDAOImpl postgresPostoDAOImpl;
	private static PostgresDistribuidoraDAOImpl postgresDistribuidoraDAOImpl;


	private PostgresFabricaDAO() {

	}

	public static void registerMeAsMain() {
		getInstance().registerAsMain();
	}

	protected static PostgresFabricaDAO getInstance() {
		return instance;
	}

	protected static PostgresFabricaDAO getNewInstance() {
		return new PostgresFabricaDAO();
	}

	public PostgresGenericDAOImplBO getGenericDAOImplBO() {
		if (postgresGenericDAOImplBO == null) {
			postgresGenericDAOImplBO = new PostgresGenericDAOImplBO();
		}
		return postgresGenericDAOImplBO;
	}

	@Override
	public PostgresPostoDAOImpl getPostgresPostoDAO() {
		if (postgresPostoDAOImpl == null) {
			postgresPostoDAOImpl = new PostgresPostoDAOImpl();
		}
		return postgresPostoDAOImpl;
	}
	
	@Override
	public PostgresDistribuidoraDAOImpl getPostgresDistribuidoraDAO() {
		if (postgresDistribuidoraDAOImpl == null) {
			postgresDistribuidoraDAOImpl = new PostgresDistribuidoraDAOImpl();
		}
		return postgresDistribuidoraDAOImpl;
	}



	
}
