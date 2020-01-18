package br.com.luisbsl.analisevendas.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.luisbsl.analisevendas.contants.ExtratorDadosContants;
import br.com.luisbsl.analisevendas.enums.ExtratorDadosEnum;
import br.com.luisbsl.analisevendas.exception.LinhaArquivoInvalidaException;
import br.com.luisbsl.analisevendas.model.Dado;
import br.com.luisbsl.analisevendas.model.Linha;
import br.com.luisbsl.analisevendas.utils.ExtratorDadosUtils;

public abstract class ExtratorDados implements IExtratorDados {
	
	private static final Logger LOGGER = Logger.getLogger(ExtratorDados.class.getName());
	protected ExtratorDadosEnum extratorTipoDadoEnum;
	
	public ExtratorDados(ExtratorDadosEnum extratorTipoDadoEnum) {
		this.extratorTipoDadoEnum = extratorTipoDadoEnum;
	}
	
	@Override
	public Boolean validarPadraoLinha(String linhaConteudo) {
		return ExtratorDadosUtils.validarPadraoLinha(extratorTipoDadoEnum.getRegexPadrao(), linhaConteudo);
	}
	
	@Override
	public Dado extrair(Linha linhaArquivo) {
		LOGGER.log(Level.INFO, () -> "Validando extraçao de dados Tipo "+extratorTipoDadoEnum);
		final String linhaConteudo = linhaArquivo.getConteudo();
		if(Boolean.FALSE.equals(validarPadraoLinha(linhaConteudo))) {
			throw new LinhaArquivoInvalidaException(linhaArquivo.toString()+"\r\nFormato inválido para o Tipo de Dados - "+extratorTipoDadoEnum);
		}
		
		LOGGER.log(Level.INFO, () -> "Extraindo dados Tipo "+extratorTipoDadoEnum);
		List<String> dados = ExtratorDadosUtils.extrairDados(linhaArquivo.getConteudo(), ExtratorDadosContants.DELIMITADOR_DADOS_PADRAO);
		return converter(linhaArquivo, dados);
	}

}
