package br.com.luisbsl.analisevendas.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.luisbsl.analisevendas.model.Cliente;
import br.com.luisbsl.analisevendas.model.Dado;
import br.com.luisbsl.analisevendas.model.Venda;
import br.com.luisbsl.analisevendas.model.Vendedor;

/**
 * 
 * Serviço de processamento de operações numa lista de Dados da Venda
 * - Calculos
 * - Filtros
 * 
 * Processa informações de Vendas extraídas do arquivo de entrada
 * 
 * @author luislima
 *
 */
public class ProcessadorVendas implements IProcessarVendas {
	
	private List<Dado> dadosVenda;
	
	public ProcessadorVendas(List<Dado> dadosVenda) {
		this.dadosVenda = dadosVenda;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long calcularQuantidadeClientes() {
		return dadosVenda
				.stream()
				.filter(Cliente.class::isInstance)
				.distinct()
				.count();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long calcularQuantidadeVendedores() {
		return dadosVenda
				.stream()
				.filter(Vendedor.class::isInstance)
				.distinct()
				.count();
	}
	
	/**
	 * Filtra Lista de Dados da Venda pelo Tipo de Dado Venda
	 * Converte para Stream<Venda>
	 * 
	 * @return Stream<Venda> retorna stream convertido
	 */
	public Stream<Venda> filtrarDadosTipoVenda() {
		return dadosVenda
				.stream()
				.filter(Venda.class::isInstance)
				.map(Venda.class::cast);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Venda filtrarVendaMaisCara() {
		return filtrarDadosTipoVenda()
				.max(Comparator.comparing(Venda::getValorTotal))
				.orElse(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Venda filtrarPiorVenda() {
		return filtrarDadosTipoVenda()
				.min(Comparator.comparing(Venda::getValorTotal))
				.orElse(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vendedor filtrarPiorVendedor() {
		return dadosVenda
				.stream()
				.filter(Vendedor.class::isInstance)
				.map(Vendedor.class::cast)
				.filter(vendedor -> vendedor.getNome().equalsIgnoreCase(filtrarPiorVenda().getVendedorNome()))
				.findFirst()
				.orElse(null);			
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdVendaMaisCara() {
		return filtrarVendaMaisCara().getId();
	}
	
	/**
	 * Retorna informações para escrita no arquivo de saída
	 * 
	 */
	@Override
	public String toString() {
		return Arrays.asList(
				"Quantidad de clientes: " +calcularQuantidadeClientes(),
				"Quantidade de vendedores: "+calcularQuantidadeVendedores(),
				"ID Venda mais cara: " +getIdVendaMaisCara(),
				"Pior vendedor: "+filtrarPiorVendedor().getNome()
		).stream().collect(Collectors.joining("\r\n"));
	}

}
