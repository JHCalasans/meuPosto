package br.com.meuposto.mbean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.gson.Gson;
import com.google.maps.model.LatLng;

import br.com.meuposto.bo.DistribuidoraBO;
import br.com.meuposto.bo.PostoBO;
import br.com.meuposto.entity.Distribuidora;
import br.com.meuposto.entity.Posto;
import br.com.meuposto.retornoServicosExtras.CoordenadasGoogle;
import br.com.meuposto.retornoServicosExtras.EnderecoCep;
import br.com.meuposto.util.ExcecoesUtil;
import br.com.meuposto.util.Paginas;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.minhaLib.util.FacesUtil;
import br.com.minhaLib.util.excecao.MsgUtil;



@ManagedBean(name="meuPostoBean")
@ViewScoped
public class MeuPostoBean extends SimpleController{

	private static final long serialVersionUID = 7192987321793570813L;
	
	private Posto posto;
	
	private String cep;
	
	private String confirmSenha;
	
	private List<Distribuidora> listaDistribuidoras = new ArrayList<Distribuidora>();
	
	private Distribuidora distribuidora;
	
	private MapModel mapModel;
	
	private LatLng coordenadas;
	
	private double latEstabelecimento;
	
	private double lngEstabelecimento;
	
	@PostConstruct
	public void carregar() {
		try {
			
			if (getSessionMap().containsKey("meuPosto.posto"))
				setPosto((Posto) getSessionMap().get("meuPosto.posto"));
			
			if(posto == null){
				posto = new Posto();
				mapModel = new DefaultMapModel();
				coordenadas = new LatLng(-10.9536484,-37.0437752);
			}
			setListaDistribuidoras(DistribuidoraBO.getInstance().obterAtivas());
			
			

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public String navegarHome(){
		getSessionMap().put("meuPosto.posto", posto);	
		return "home.proj?faces-redirect=true";
	}
	
	public void addMarker() {
        Marker marker = new Marker(new org.primefaces.model.map.LatLng(latEstabelecimento, lngEstabelecimento), "Meu Posto");
        mapModel.getMarkers().clear();
        mapModel.addOverlay(marker);
    }
	
	public void salvarPosto(){
		if(posto.getEstado() == null || posto.getEstado().isEmpty()){
			MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Favor validar CEP!");
			
		}
		
		if(!posto.getSenha().equals(confirmSenha)){
			MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Confirmação de senha diferente da senha!");
			 
		}
		
		try {
			posto.setLatitude(latEstabelecimento);
			posto.setLongitude(lngEstabelecimento);
			posto = PostoBO.getInstance().salvarPosto(posto);
			MsgUtil.updateMessage(FacesMessage.SEVERITY_INFO, "Posto cadastrado com sucesso.!");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgSalvo').show();");
			
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void salvarPreco(){

		try {
			posto.setLatitude(latEstabelecimento);
			posto.setLongitude(lngEstabelecimento);
			posto = PostoBO.getInstance().salvarPosto(posto);
			MsgUtil.updateMessage(FacesMessage.SEVERITY_INFO, "Posto cadastrado com sucesso.!");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgSalvo').show();");
			
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void desativarPosto(){

		try {
			posto = PostoBO.getInstance().desativarPosto(posto);
			MsgUtil.updateMessage(FacesMessage.SEVERITY_INFO, "Posto desativado com sucesso.!");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg').hide();");
			FacesUtil.redirecionar(null, logout(), true, null);
			
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void reativarPosto(){

		try {
			posto = PostoBO.getInstance().reativarPosto(posto);
			MsgUtil.updateMessage(FacesMessage.SEVERITY_INFO, "Posto reativado com sucesso.!");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgReativar').hide();");
			
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	
	public void validarCep() {
		if (this.cep != null && this.cep.replace("-", "").replace("_", "").length() == 8) {
			try {

				HttpClient httpClient = HttpClients.custom().build();

				HttpUriRequest request = RequestBuilder.get().setUri("http://www.viacep.com.br/ws/" + this.cep.replace("-", "") + "/json/")
															 .setHeader("accept", "application/json")
															 .build();
				

				HttpResponse response = httpClient.execute(request);

				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				String output;
				String json = "";
				while ((output = br.readLine()) != null) {
					json += output + "\n";
				}
				

				
				Gson gson = new Gson();
				EnderecoCep end = gson.fromJson(json, EnderecoCep.class);
				this.getPosto().setCep(this.cep);
				this.getPosto().setCidade(end.getLocalidade());
				this.getPosto().setLogradouro(end.getLogradouro());
				this.getPosto().setSiglaEstado(end.getUf());
				this.getPosto().setBairro(end.getBairro());
				
				
				switch (end.getUf()) {
				case "RO":
					this.getPosto().setEstado("Rondônia");
					break;
				case "AC":
					this.getPosto().setEstado("Acre");
					break;
				case "AM":
					this.getPosto().setEstado("Amazonas");
					break;
				case "RR":
					this.getPosto().setEstado("Roraima");
					break;
				case "PA":
					this.getPosto().setEstado("Pará");
					break;
				case "AP":
					this.getPosto().setEstado("Amapá");
					break;
				case "TO":
					this.getPosto().setEstado("Tocantins");
					break;
				case "MA":
					this.getPosto().setEstado("Maranhão");
					break;
				case "PI":
					this.getPosto().setEstado("Piauí");
					break;
				case "CE":
					this.getPosto().setEstado("Ceará");
					break;
				case "RN":
					this.getPosto().setEstado("Rio Grande do Norte");
					break;
				case "PB":
					this.getPosto().setEstado("Paraíba");
					break;
				case "PE":
					this.getPosto().setEstado("Pernambuco");
					break;
				case "AL":
					this.getPosto().setEstado("Alagoas");
					break;
				case "SE":
					this.getPosto().setEstado("Sergipe");
					break;
				case "BA":
					this.getPosto().setEstado("Bahia");
					break;
				case "MG":
					this.getPosto().setEstado("Minas Gerais");
					break;
				case "ES":
					this.getPosto().setEstado("Espírito Santo");
					break;
				case "RJ":
					this.getPosto().setEstado("Rio De Janeiro");
					break;
				case "SP":
					this.getPosto().setEstado("São Paulo");
					break;
				case "PR":
					this.getPosto().setEstado("Paraná");
					break;
				case "SC":
					this.getPosto().setEstado("Santa Catarina");
					break;
				case "RS":
					this.getPosto().setEstado("Rio Grande Do Sul");
					break;
				case "MS":
					this.getPosto().setEstado("Mato Grosso Do Sul");
					break;
				case "MT":
					this.getPosto().setEstado("Mato Grosso");
					break;
				case "GO":
					this.getPosto().setEstado("Goiás");
					break;
				case "DF":
					this.getPosto().setEstado("Distrito Federal");
					break;
				}
					
				buscarCoordenadas();
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('dlMap').show();");

			} catch (Exception e) {
				e.printStackTrace();
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível buscar informações do CEP!.", "");
			}
		} else {
			MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "CEP inválido!.", "");
		}

	}
	
	
	private void buscarCoordenadas(){
		
		
		try {
			HttpClient httpClient = HttpClients.custom().build();
			
			//Buscando coordenadas pelo cep passado
			HttpUriRequest requestCoordenadas = RequestBuilder.get().setUri("https://maps.googleapis.com/maps/api/geocode/json?address=" + this.cep.replace("-", "")+"+BR&key=AIzaSyBYUVGXHsK1iaTzuDZ5LT1tLBMq4LtV-Hc" )
					 .setHeader("accept", "application/json")
					 .build();
			
			HttpResponse response;
			response = httpClient.execute(requestCoordenadas);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			String json = "";
			while ((output = br.readLine()) != null) {
				json += output + "\n";	
			}
				
			Gson gson = new Gson();
			CoordenadasGoogle coordGoogle = gson.fromJson(json, CoordenadasGoogle.class);
			coordenadas.lat = coordGoogle.getResults().get(0).getGeometry().getLocation().getLat();		
			coordenadas.lng = coordGoogle.getResults().get(0).getGeometry().getLocation().getLng();
			
		} catch (Exception e) {
			e.printStackTrace();
			MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar coordenadas do mapa pelo CEP!.", "");
		}

	

	}
	
	
	public void teste(){
		System.out.println(latEstabelecimento+ " -- " + lngEstabelecimento);
		
	}

	public Posto getPosto() {
		return posto;
	}

	public void setPosto(Posto posto) {
		this.posto = posto;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public List<Distribuidora> getListaDistribuidoras() {
		return listaDistribuidoras;
	}

	public void setListaDistribuidoras(List<Distribuidora> listaDistribuidoras) {
		this.listaDistribuidoras = listaDistribuidoras;
	}

	public Distribuidora getDistribuidora() {
		return distribuidora;
	}

	public void setDistribuidora(Distribuidora distribuidora) {
		this.distribuidora = distribuidora;
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public LatLng getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(LatLng coordenadas) {
		this.coordenadas = coordenadas;
	}

	public double getLatEstabelecimento() {
		return latEstabelecimento;
	}

	public void setLatEstabelecimento(double latEstabelecimento) {
		this.latEstabelecimento = latEstabelecimento;
	}

	public double getLngEstabelecimento() {
		return lngEstabelecimento;
	}

	public void setLngEstabelecimento(double lngEstabelecimento) {
		this.lngEstabelecimento = lngEstabelecimento;
	}

	public String getConfirmSenha() {
		return confirmSenha;
	}

	public void setConfirmSenha(String confirmSenha) {
		this.confirmSenha = confirmSenha;
	}
	

}
