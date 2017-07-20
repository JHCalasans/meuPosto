package br.com.meuposto.entity;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.minhaLib.dao.Entidade;


@Entity
@Table(name = Distribuidora.nomeTabela, schema = Distribuidora.esquema, catalog = "meuposto")
public class Distribuidora extends Entidade{


	private static final long serialVersionUID = -4695514174990651276L;

	public final static String esquema = "meuposto";
	public final static String nomeTabela = "distribuidora";
	
	@Id
	@Column(name = "cod_distribuidora", nullable = false)
	@SequenceGenerator(name = "distribuidora_cod_distribuidora_seq", sequenceName = "meuposto.distribuidora_cod_distribuidora_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distribuidora_cod_distribuidora_seq")
	private Integer codigo;
	
	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;
	
	@Column(name = "logo", nullable = true)
	private byte[] logo;
	
	@Column(name = "flg_ativo", nullable = true)
	private boolean ativo;
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public StreamedContent getStreamedLogo(){
		return  new DefaultStreamedContent(
				new ByteArrayInputStream(logo),"image/*");
	}

}
