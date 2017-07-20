package br.com.meuposto.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.meuposto.bo.DistribuidoraBO;
import br.com.meuposto.entity.Distribuidora;
import br.com.meuposto.util.ExcecoesUtil;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;




@FacesConverter("distribuidoraConverter")
public class DistribuidoraConverter implements Converter{

	
	private static Map<String, Distribuidora> distribuidoras = new HashMap<String, Distribuidora>();
	
	static {
		List<Distribuidora> list;
		try {
			list = DistribuidoraBO.getInstance().obterAtivas();
			for (Distribuidora dist : list) {
				distribuidoras.put(dist.getCodigo().toString(), dist);
			}
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
		
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2 != null ? distribuidoras.get(arg2) : null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2 != null ? ((Distribuidora)arg2).getCodigo().toString() : null;
	}

}
