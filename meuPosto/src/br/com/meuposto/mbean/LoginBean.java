package br.com.meuposto.mbean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.meuposto.bo.PostoBO;
import br.com.meuposto.entity.Posto;
import br.com.meuposto.util.ExcecoesUtil;
import br.com.meuposto.util.FacesUtil;
import br.com.meuposto.util.Paginas;


@ManagedBean(name = LoginBean.NOME_BEAN)
@ViewScoped
public class LoginBean extends SimpleController {

	private static final long serialVersionUID = 524489573749923264L;
	public static final String NOME_BEAN = "loginBean";
	
	private String login; 
	private String senha;
	
	private String cnpj;
	private String email;

	private boolean exibeBannerMenu = true;
	
	
	@PostConstruct
	public void carregar() {
		try {
			if (getSessionMap().containsKey("meuPosto.posto"))
				FacesUtil.redirecionar(null, Paginas.PAG_HOME, true, null);
			

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	

	public boolean isExibeBannerMenu() {
		return exibeBannerMenu;
	}

	public void setExibeBannerMenu(boolean exibeBannerMenu) {
		this.exibeBannerMenu = exibeBannerMenu;
	}

	public LoginBean() {

	}

	public void redirecionarHome() {
		try {
			FacesUtil.redirecionar(null, Paginas.PAG_LOGIN, true, null);
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void logar() {
		try {
			Posto posto = PostoBO.getInstance().obterPostoPorCNPJESenha(login, senha);
			if(posto != null){
				getSessionMap().put("meuPosto.posto", posto);	
				FacesUtil.redirecionar(null, Paginas.PAG_HOME, true, null);
			}else
				addMsg(FacesMessage.SEVERITY_ERROR, "CNPJ/Senha incorretos.");
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


}
