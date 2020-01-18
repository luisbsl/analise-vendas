package br.com.luisbsl.analisevendas.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;

import br.com.luisbsl.analisevendas.contants.ExtratorDadosContants;

public final class ExtratorDadosUtils {

	private ExtratorDadosUtils() {
	}

	public static List<String> extrairDados(String linha, String delimitador) {
		return Splitter.on(delimitador).trimResults().omitEmptyStrings().splitToList(linha);
	}
	
	public static Boolean validarPadraoLinha(String padrao, String linha) {
		Pattern pattern = Pattern.compile(padrao);
	    Matcher matcher = pattern.matcher(linha);
	    return matcher.find();
	}
	
	public static String extrairTipoDado(String linha) {
		Pattern pattern = Pattern.compile(ExtratorDadosContants.PADRAO_REGEX_TIPO_DADO);
	    Matcher matcher = pattern.matcher(linha);
	    matcher.find();
	    return matcher.group();	    
	}
	
	public static List<String> extrairDadosItens(String linha) {
		List<String> dadosItem = new ArrayList<>();
		Pattern pattern = Pattern.compile(ExtratorDadosContants.PADRAO_REGEX_DADOS_ITEM);
	    Matcher matcher = pattern.matcher(linha);
	    while(matcher.find()) {
	    	dadosItem.add(matcher.group());
	    }
	    return dadosItem;
	}

}
