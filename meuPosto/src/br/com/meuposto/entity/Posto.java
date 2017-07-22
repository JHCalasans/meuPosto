package br.com.meuposto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Posto.nomeTabela, schema = Posto.esquema, catalog = "meuposto")
@NamedQueries(value = { 
		@NamedQuery(name = "Posto.obterPorDistancia", query = "select p from Posto p"
		+ " where p.latitude >= (:latitude - 0.2) and p.latitude <= (:latitude + 0.2) and p.longitude >= (:longitude - 0.2)  and p.longitude <= (:longitude + 0.2)"),
		@NamedQuery(name = "Posto.obterPorCNPJ", query = "select p from Posto p where p.cnpj like :cnpj"),
		@NamedQuery(name = "Posto.obterPorCNPJESenha", query = "select p from Posto p where p.cnpj like :cnpj and senha like :senha")
		})
public class Posto extends Entidade {

	private static final long serialVersionUID = -8805523089808569852L;

	public final static String esquema = "meuposto";
	public final static String nomeTabela = "posto";

	@Id
	@Column(name = "cod_posto", nullable = false)
	@SequenceGenerator(name = "posto_cod_posto_seq", sequenceName = "meuposto.posto_cod_posto_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posto_cod_posto_seq")
	private Integer codigo;

	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	@Column(name = "cnpj", nullable = true, length = 50)
	private String cnpj;

	@Column(name = "senha", nullable = true)
	private String senha;

	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "longitude", nullable = false)
	private double longitude;

	@Column(name = "cep", nullable = true)
	private String cep;

	@Column(name = "numero", nullable = true)
	private String numero;

	@Column(name = "logradouro", nullable = true)
	private String logradouro;

	@Column(name = "bairro", nullable = true)
	private String bairro;

	@Column(name = "gasolina_comum", nullable = true)
	private Float gasolinaComum;

	@Column(name = "diesel", nullable = true)
	private Float diesel;

	@Column(name = "alcool", nullable = true)
	private Float alcool;

	@Column(name = "gasolina_aditivada", nullable = true)
	private Float gasolinaAditivada;

	@Column(name = "gnv", nullable = true)
	private Float gnv;

	@Column(name = "estado", nullable = true)
	private String estado;

	@Column(name = "cidade", nullable = true)
	private String cidade;

	@Column(name = "sigla_estado", nullable = true)
	private String siglaEstado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_distribuidora", nullable = false, referencedColumnName = "cod_distribuidora")
	private Distribuidora distribuidora;

	@Column(name = "flg_ativo", nullable = false)
	private boolean ativo;

	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;

	@Column(name = "dt_desativacao", nullable = true)
	private Date dataDesativacao;

	@Override
	public Serializable getIdentificador() {

		return getCodigo();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Float getGasolinaComum() {
		return gasolinaComum;
	}

	public void setGasolinaComum(Float gasolinaComum) {
		this.gasolinaComum = gasolinaComum;
	}

	public Float getDiesel() {
		return diesel;
	}

	public void setDiesel(Float diesel) {
		this.diesel = diesel;
	}

	public Float getAlcool() {
		return alcool;
	}

	public void setAlcool(Float alcool) {
		this.alcool = alcool;
	}

	public Float getGasolinaAditivada() {
		return gasolinaAditivada;
	}

	public void setGasolinaAditivada(Float gasolinaAditivada) {
		this.gasolinaAditivada = gasolinaAditivada;
	}

	public Float getGnv() {
		return gnv;
	}

	public void setGnv(Float gnv) {
		this.gnv = gnv;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataDesativacao() {
		return dataDesativacao;
	}

	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Distribuidora getDistribuidora() {
		return distribuidora;
	}

	public void setDistribuidora(Distribuidora distribuidora) {
		this.distribuidora = distribuidora;
	}

}
