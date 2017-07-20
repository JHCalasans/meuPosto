package br.com.meuposto.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.meuposto.dao.IPostoDAO;
import br.com.meuposto.entity.Posto;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;

@PersistenceContext(unitName = "postgresPU")
public class PostgresPostoDAOImpl  extends GenericDAOImpl<Posto, Integer>
implements IPostoDAO{

	@Override
	public List<Posto> obterporDistancia(double latitude, double longitude, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		return findByNamedQueryAndNamedParams("Posto.obterPorDistancia", params, em);
	}

	@Override
	public List<Posto> obterporCNPJ(String cnpj, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cnpj", cnpj);
		return findByNamedQueryAndNamedParams("Posto.obterPorCNPJ", params, em);
	}

}
