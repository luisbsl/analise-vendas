package br.com.luisbsl.analisevendas.model.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.luisbsl.analisevendas.exception.DadoInvalidoException;
import br.com.luisbsl.analisevendas.model.Vendedor;
import br.com.luisbsl.analisevendas.test.constants.DadosMocados;

/**
 * Casos de testes para classe VendedorBuilder
 * 
 * @see VendedorBuilder
 * @author luislima
 *
 */
public class VendedorBuilderTest {
	
	@Test
	void buildTest() {
		Vendedor vendedor = new VendedorBuilder()
									.comCpf("1234567891234")
									.comNome("Pedro")
									.comSalario(50000.0)
									.build();
		
		Vendedor vendedorTest = (Vendedor) DadosMocados.VENDEDORES.stream().filter(v -> v.equals(vendedor)).findFirst().get();
		
		assertNotNull(vendedor);
		assertNotNull(vendedorTest);
		assertEquals(vendedor, vendedorTest);
	}
	
	@Test
	void buildTest_deveLevantarExcecao_DadoInvalidoException_CPF() {
		Exception exception = assertThrows(
				DadoInvalidoException.class, 
				() -> new VendedorBuilder()
							.comCpf("")
							.comNome("Pedro")
							.comSalario(50000.0)
							.build());
		
		assertTrue(exception.getMessage().contains("CPF"));
	}
	
	@Test
	void buildTest_deveLevantarExcecao_DadoInvalidoException_Nome() {
		Exception exception = assertThrows(
				DadoInvalidoException.class, 
				() -> new VendedorBuilder()
							.comCpf("1234567891234")
							.comNome(null)
							.comSalario(50000.0)
							.build());
		
		assertTrue(exception.getMessage().contains("Nome"));
	}
	
	@Test
	void buildTest_deveLevantarExcecao_DadoInvalidoException_Salario() {
		Exception exception = assertThrows(
				DadoInvalidoException.class, 
				() -> new VendedorBuilder()
							.comCpf("1234567891234")
							.comNome("Pedro")
							.comSalario(null)
							.build());

		assertTrue(exception.getMessage().contains("Salário"));
	}
	
	@Test
	void buildTest_deveLevantarExcecao_NumberFormatException() {
		String salario = "salario";
		assertThrows(
				NumberFormatException.class, 
				() -> new VendedorBuilder()
							.comCpf("1234567891234")
							.comNome("Pedro")
							.comSalario(Double.valueOf(salario))
							.build());
	}

}
