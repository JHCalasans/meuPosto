package br.com.meuposto.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.meuposto.entity.Posto;
import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;

public interface IPostoDAO extends GenericDAO<Posto, Integer> {

	static CriterioOrdenacao BY_NOME_ASC = CriterioOrdenacao.asc("nome");
	
	public List[] obterporDistancia(double latitude, double longitude,EntityManager em) throws ExcecaoBanco;
	
	public List[] obterporCombustivel(double latitude, double longitude, int tpCombustivel,EntityManager em) throws ExcecaoBanco;
	
	public List<Posto> obterporCNPJ(String cnpj,EntityManager em) throws ExcecaoBanco;
	
	public List<Posto> obterporCNPJESenha(String cnpj, String senha,EntityManager em) throws ExcecaoBanco;

}
