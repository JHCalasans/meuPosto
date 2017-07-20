package br.com.meuposto.dao;

import br.com.meuposto.entity.Distribuidora;
import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;

public interface IDistribuidoraDAO  extends GenericDAO<Distribuidora, Integer>{
	
	static CriterioOrdenacao BY_DSC_ASC = CriterioOrdenacao.asc("descricao");

}
