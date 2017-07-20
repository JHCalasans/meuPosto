package br.com.meuposto.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.meuposto.dao.IDistribuidoraDAO;
import br.com.meuposto.entity.Distribuidora;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;

public class DistribuidoraBO extends MeuPostoBO {

	private static DistribuidoraBO instance;

	private DistribuidoraBO() {

	}

	public static DistribuidoraBO getInstance() {
		if (instance == null)
			instance = new DistribuidoraBO();

		return instance;
	}
	
	
	public List<Distribuidora> obterAtivas() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IDistribuidoraDAO distribuidoraDAO = fabricaDAO.getPostgresDistribuidoraDAO();
			Distribuidora distribuidora = new Distribuidora();
			distribuidora.setAtivo(true);
			List<Distribuidora> result = distribuidoraDAO.findByExample(distribuidora, distribuidoraDAO.BY_DSC_ASC);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (ExcecaoBanco e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter distribuidoras ativas.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
