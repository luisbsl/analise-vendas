package br.com.luisbsl.analisevendas.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.luisbsl.analisevendas.service.ExtratorDadosCliente;
import br.com.luisbsl.analisevendas.service.ExtratorDadosVenda;
import br.com.luisbsl.analisevendas.service.ExtratorDadosVendedor;
import br.com.luisbsl.analisevendas.service.IExtratorDados;
import br.com.luisbsl.analisevendas.test.constants.DadosMocados;

public class TipoDadoEnumTest {
	
	@Test
	void getIdentificadorTest_deveRetornarIdentificador_Vendedor() {
		String identificadorVendedor = TipoDadoEnum.VENDEDOR.getIdentificador();
		
		assertNotNull(identificadorVendedor);
		assertEquals(DadosMocados.ID_TIPO_DADO_VENDEDOR, identificadorVendedor);
	}
	
	@Test
	void getIdentificadorTest_deveRetornarIdentificador_Cliente() {
		String identificadorCliente = TipoDadoEnum.CLIENTE.getIdentificador();
		
		assertNotNull(identificadorCliente);
		assertEquals(DadosMocados.ID_TIPO_DADO_CLIENTE, identificadorCliente);
	}
	
	@Test
	void getIdentificadorTest_deveRetornarIdentificador_Venda() {
		String identificadorVenda = TipoDadoEnum.VENDA.getIdentificador();
		
		assertNotNull(identificadorVenda);
		assertEquals(DadosMocados.ID_TIPO_DADO_VENDA, identificadorVenda);
	}
	
	@Test
	void getIdentificadorTest_deveRetornarExtrator_Vendedor() {
		IExtratorDados extratorVendedor = TipoDadoEnum.VENDEDOR.obterExtratorDado();
		
		assertNotNull(extratorVendedor);
		assertTrue(extratorVendedor instanceof ExtratorDadosVendedor);
	}
	
	@Test
	void getIdentificadorTest_deveRetornarExtrator_Cliente() {
		IExtratorDados extratorCliente = TipoDadoEnum.CLIENTE.obterExtratorDado();
		
		assertNotNull(extratorCliente);
		assertTrue(extratorCliente instanceof ExtratorDadosCliente);
	}
	
	@Test
	void getIdentificadorTest_deveRetornarExtrator_Venda() {
		IExtratorDados extratorVenda = TipoDadoEnum.VENDA.obterExtratorDado();
		
		assertNotNull(extratorVenda);
		assertTrue(extratorVenda instanceof ExtratorDadosVenda);
	}

}
