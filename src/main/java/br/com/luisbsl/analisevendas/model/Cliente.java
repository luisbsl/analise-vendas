package br.com.luisbsl.analisevendas.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

/**
 * Classe que representa um Cliente extraído do arquivo
 * 
 * @author luislima
 *
 */
public class Cliente implements Dado, Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message="CNPJ do cliente não pode ser nulo ou vazio")
	private String cnpj;
	
	@NotBlank(message="Nome do cliente não pode ser nulo ou vazio")
	private String nome;
	
	@NotBlank(message="Area de Negócio do cliente não pode ser nula ou vazia")
	private String areaNegocio;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAreaNegocio() {
		return areaNegocio;
	}

	public void setAreaNegocio(String areaNegocio) {
		this.areaNegocio = areaNegocio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [cnpj=" + cnpj + ", nome=" + nome + ", areaNegocio=" + areaNegocio + "]";
	}

}
