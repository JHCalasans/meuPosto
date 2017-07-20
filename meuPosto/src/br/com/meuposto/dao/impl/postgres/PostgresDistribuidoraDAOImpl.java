package br.com.meuposto.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.meuposto.dao.IDistribuidoraDAO;
import br.com.meuposto.entity.Distribuidora;
import br.com.minhaLib.dao.impl.GenericDAOImpl;

@PersistenceContext(unitName = "postgresPU")
public class PostgresDistribuidoraDAOImpl extends GenericDAOImpl<Distribuidora, Integer> implements IDistribuidoraDAO{

}
