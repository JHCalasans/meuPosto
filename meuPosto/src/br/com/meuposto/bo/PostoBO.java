package br.com.meuposto.bo;

import java.text.DecimalFormat;
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
			List[] lista = postoDAO.obterporDistancia(latitude, longitude, em);
			List<Posto> result = (List<Posto>)lista[0];
			List<Double> distancias =   (List<Double>)lista[1];
			int i = 0;
			DecimalFormat df2 = new DecimalFormat(".##");
			for(Posto posto : result){
				posto.setDistancia(distancias.get(i));
				if(distancias.get(i) >= 1)
					posto.setDistanciaDoPonto("("+ df2.format(distancias.get(i)) +" Km)");
				else
					posto.setDistanciaDoPonto("("+ String.valueOf(distancias.get(i) * 1000).substring(0, 3) +" mts)");
				
				i++;
			}
			emUtil.commitTransaction(transaction);
			return result;
		} catch (ExcecaoBanco e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter posto pela distância.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<Posto> obterPostoPorCombustivel(double latitude, double longitude, int tpCombustivel) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPostoDAO postoDAO = fabricaDAO.getPostgresPostoDAO();
			List[] lista = postoDAO.obterporCombustivel(latitude, longitude, tpCombustivel, em);
			List<Posto> result = (List<Posto>)lista[0];
			List<Double> distancias =   (List<Double>)lista[1];
			int i = 0;
			DecimalFormat df2 = new DecimalFormat(".##");
			for(Posto posto : result){
				posto.setDistancia(distancias.get(i));
				if(distancias.get(i) >= 1)
					posto.setDistanciaDoPonto("("+ df2.format(distancias.get(i)) +" Km)");
				else
					posto.setDistanciaDoPonto("("+ String.valueOf(distancias.get(i) * 1000).substring(0, 3) +" mts)");
				
				i++;
			}
			emUtil.commitTransaction(transaction);
			return result;
		} catch (ExcecaoBanco e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter posto pela distância.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Posto obterPostoPorCNPJESenha(String cnpj, String senha) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Posto result = null;
		try {
			transaction.begin();
			IPostoDAO postoDAO = fabricaDAO.getPostgresPostoDAO();

			List<Posto> lista = postoDAO.obterporCNPJESenha(cnpj, FuncoesUtil.criptografarSenha(senha), em);
			if (lista != null && lista.size() > 0)
				result = lista.get(0);

			emUtil.commitTransaction(transaction);
			return result;
		} catch (ExcecaoBanco e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter posto pelo CNPJ.", e);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar criptografar senha.", e);
		}		finally {
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
	
	public Posto desativarPosto(Posto posto) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPostoDAO postoDAO = fabricaDAO.getPostgresPostoDAO();
			posto.setAtivo(false);
			posto.setDataDesativacao(new Date());
			posto = postoDAO.save(posto, em);
			emUtil.commitTransaction(transaction);
			return posto;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar desativar posto.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Posto reativarPosto(Posto posto) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPostoDAO postoDAO = fabricaDAO.getPostgresPostoDAO();
			posto.setAtivo(true);
			posto.setDataDesativacao(null);
			posto = postoDAO.save(posto, em);
			emUtil.commitTransaction(transaction);
			return posto;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar reativar posto.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
