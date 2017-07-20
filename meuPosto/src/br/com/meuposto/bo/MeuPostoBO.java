package br.com.meuposto.bo;

import org.apache.log4j.Logger;

import br.com.meuposto.dao.FabricaDAO;
import br.com.minhaLib.bo.BaseBO;
import br.com.minhaLib.util.EntityManagerUtil;

public class MeuPostoBO extends BaseBO{
	
	protected FabricaDAO fabricaDAO;
	protected EntityManagerUtil emUtil;
	private static Logger log = Logger.getLogger(BaseBO.class);

	public EntityManagerUtil getEmUtil() {
		return emUtil;
	}

	public void setEmUtil(EntityManagerUtil emUtil) {
		this.emUtil = emUtil;
	}

	public MeuPostoBO() {
		super();
		fabricaDAO = FabricaDAO.getFabricaDAO();
		emUtil = new EntityManagerUtil("postgresPU");
	}

}
