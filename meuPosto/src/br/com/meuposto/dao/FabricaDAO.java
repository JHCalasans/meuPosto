package br.com.meuposto.dao;

public abstract class FabricaDAO {

	private static FabricaDAO instance;

	public abstract IPostoDAO getPostgresPostoDAO();

	public abstract IDistribuidoraDAO getPostgresDistribuidoraDAO();


	/**
	 * Para utilizar de fato a classe {@link FabricaDAO}, uma implementação
	 * sua deve se registrar como sendo a instância principal usando
	 * {@code instancia.registerAsMain()}.
	 */
	protected final void registerAsMain() {
		FabricaDAO.instance = this;
	}

	public static FabricaDAO getFabricaDAO() {
		return instance;
	}

}
