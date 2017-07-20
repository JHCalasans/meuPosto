package br.com.meuposto.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.meuposto.entity.GenericEntity;
import br.com.minhaLib.dao.impl.GenericDAOImpl;

@PersistenceContext(unitName = "postgresPU")
public class PostgresGenericDAOImplBO extends
		GenericDAOImpl<GenericEntity, Integer> {

}
