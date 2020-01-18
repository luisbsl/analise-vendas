package br.com.luisbsl.analisevendas.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SistemaOperacionalUtilsTest {
	
	@Test
	void getDiretorioArvoreTest() {
		String diretorioArvore = SistemaOperacionalUtils.getDiretorioArvore(Arrays.asList("data", "in"));
		
		assertNotNull(diretorioArvore);
		assertEquals(System.getProperty("user.home")+"/data/in/", diretorioArvore);
	}
	
	@Test
	void getDiretorioArvoreSeparadorTest() {
		String separador = SistemaOperacionalUtils.getDiretorioArvoreDelimitador();
		
		assertNotNull(separador);
		assertEquals("/", separador);
		assertNotEquals("\\", separador);
	}
	
	@Test
	void verificarDiretorio() throws IOException {
		SistemaOperacionalUtils.verificarDiretorio(SistemaOperacionalUtils.getDiretorioArvore(Arrays.asList("data", "in")));
		SistemaOperacionalUtils.verificarDiretorio(SistemaOperacionalUtils.getDiretorioArvore(Arrays.asList("data", "out")));
		
		assertTrue(Boolean.TRUE.equals(new File(SistemaOperacionalUtils.getDiretorioArvore(Arrays.asList("data", "in"))).exists()));
		assertTrue(Boolean.TRUE.equals(new File(SistemaOperacionalUtils.getDiretorioArvore(Arrays.asList("data", "out"))).exists()));
	}

}
