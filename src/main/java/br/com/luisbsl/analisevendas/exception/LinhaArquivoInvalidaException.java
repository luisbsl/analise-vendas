package br.com.luisbsl.analisevendas.exception;

import br.com.luisbsl.analisevendas.enums.ExtratorDadosEnum;
import br.com.luisbsl.analisevendas.utils.ExtratorDadosUtils;

/**
 * Exceção que ocorre quando o padrão da linha não está compatível com o padrão Regex específico
 * de acordo com o Tipo de Dado correspondente da linha
 * 
 * @see {@link ExtratorDadosEnum}
 * @see {@link ExtratorDadosUtils#validarPadraoLinha(String, String)}
 * @author luislima
 *
 */
public class LinhaArquivoInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LinhaArquivoInvalidaException(String mensagemErro) {
		super(mensagemErro);
	}

}
