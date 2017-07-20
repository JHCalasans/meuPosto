package br.com.meuposto.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.meuposto.dao.IPostoDAO;
import br.com.meuposto.entity.Posto;
import br.com.meuposto.util.FuncoesUtil;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;

public class PostoBO extends MeuPostoBO {

	private static PostoBO instance;

	private PostoBO() {

	}

	public static PostoBO getInstance() {
		if (instance == null)
			instance = new PostoBO();

		return instance;
	}

	public List<Posto> obterPostoPorDistancia(double latitude, double longitude) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPostoDAO postoDAO = fabricaDAO.getPostgresPostoDAO();
			List<Posto> result = postoDAO.obterporDistancia(latitude, longitude, em);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (ExcecaoBanco e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter posto pela dist�ncia.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Posto salvarPosto(Posto posto) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPostoDAO postoDAO = fabricaDAO.getPostgresPostoDAO();
			posto.setAtivo(true);
			posto.setDataCriacao(new Date());
			posto.setSenha(FuncoesUtil.criptografarSenha(posto.getSenha()));
			List<Posto> lista = postoDAO.obterporCNPJ(posto.getCnpj(), em);
			if (lista != null && lista.size() > 0) {
				emUtil.commitTransaction(transaction);
				throw new ExcecaoNegocio("Já existe um registro com esse CNPJ.!");
			}
			posto = postoDAO.save(posto, em);
			emUtil.commitTransaction(transaction);
			return posto;
		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio(e.getMessage(), e);
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar posto.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
