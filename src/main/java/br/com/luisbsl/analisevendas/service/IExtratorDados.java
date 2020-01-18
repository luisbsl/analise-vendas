package br.com.luisbsl.analisevendas.service;

import java.util.List;

import br.com.luisbsl.analisevendas.model.Dado;
import br.com.luisbsl.analisevendas.model.Linha;

public interface IExtratorDados {
	
	public Boolean validarPadraoLinha(String linha);
	public Dado converter(Linha linhaArquivo, List<String> dados);
	public Dado extrair(Linha linhaArquivo);

}
