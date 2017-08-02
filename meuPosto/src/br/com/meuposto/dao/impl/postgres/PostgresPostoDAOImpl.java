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
	public List[] obterporDistancia(double latitude, double longitude, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("pi", Math.PI);
		return findByNamedQueryAndNamedParams("Posto.obterPorDistancia", params, em, Posto.class, Double.class);
	}

	@Override
	public List<Posto> obterporCNPJ(String cnpj, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cnpj", cnpj);
		return findByNamedQueryAndNamedParams("Posto.obterPorCNPJ", params, em);
	}

	@Override
	public List<Posto> obterporCNPJESenha(String cnpj, String senha, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cnpj", cnpj);
		params.put("senha", senha);
		return findByNamedQueryAndNamedParams("Posto.obterPorCNPJESenha", params, em);
	}

	@Override
	public List[] obterporCombustivel(double latitude, double longitude, int tpCombustivel, EntityManager em)
			throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("pi", Math.PI);
		switch (tpCombustivel) {
		case 1:
			return findByNamedQueryAndNamedParams("Posto.obterPorGasolinaComum", params, em, Posto.class, Double.class);
			
		case 2:
			return findByNamedQueryAndNamedParams("Posto.obterPorGasolinaAditivada", params, em, Posto.class, Double.class);
		
		case 3:
			return findByNamedQueryAndNamedParams("Posto.obterPorAlcool", params, em, Posto.class, Double.class);
	
		case 4:
			return findByNamedQueryAndNamedParams("Posto.obterPorDiesel", params, em, Posto.class, Double.class);

		case 5:
			return findByNamedQueryAndNamedParams("Posto.obterPorGnv", params, em, Posto.class, Double.class);
		default:
			return null;
		
		}
		
	}
	

}
