package br.com.luisbsl.analisevendas.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Classe que representa um Item extraído do arquivo
 * 
 * @author luislima
 *
 */
public class Item implements Dado, Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "ID do Item não pode ser nulo")
	private Integer id;

	@NotNull(message = "Quantidade do Item não pode ser nula")
	private Integer quantidade;

	@NotNull(message = "Preço do Item não pode ser nulo")
	private Double preco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	/**
	 * 
	 * @return retorna o valor total do item (preco * quantidade)
	 */
	public Double getValorTotal() {
		return preco * quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", quantidade=" + quantidade + ", preco=" + preco + "]";
	}

}
