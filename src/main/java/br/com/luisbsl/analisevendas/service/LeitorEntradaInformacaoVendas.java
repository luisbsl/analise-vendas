package br.com.luisbsl.analisevendas.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.Files;

import br.com.luisbsl.analisevendas.exception.ConversaoDadoException;
import br.com.luisbsl.analisevendas.exception.DadoInvalidoException;
import br.com.luisbsl.analisevendas.exception.LinhaArquivoInvalidaException;
import br.com.luisbsl.analisevendas.exception.TipoDadoNaoEncontradoException;
import br.com.luisbsl.analisevendas.model.Dado;
import br.com.luisbsl.analisevendas.model.Linha;
import br.com.luisbsl.analisevendas.service.facade.ExtratorDadoFacade;
import br.com.luisbsl.analisevendas.utils.SistemaOperacionalUtils;

/**
 * 
 * @author luislima
 *
 */
public final class LeitorEntradaInformacaoVendas {

	private static final Logger LOGGER = Logger.getLogger(LeitorEntradaInformacaoVendas.class.getName());

	private static final String DIRETORIO_ENTRADA_PADRAO = SistemaOperacionalUtils.getDiretorioArvore(Arrays.asList("data", "in"));
	
	private static final String MIMETYPE_ACEITO = "text/plain";
	private static final Path path = Paths.get(DIRETORIO_ENTRADA_PADRAO);

	private LeitorEntradaInformacaoVendas() {
	}

	/**
	 * Inicia observer no diretório para detecção de novos arquivos inseridos
	 * 
	 * @see {@link WatchService}
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void iniciar() throws IOException, InterruptedException {
		SistemaOperacionalUtils.verificarDiretorio(DIRETORIO_ENTRADA_PADRAO);
		WatchService watchService = FileSystems.getDefault().newWatchService();
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				lerArquivo(event.context().toString());
			}
		}
	}

	public static void lerArquivo(String nomeArquivoDetectado) throws IOException {
		File novoArquivo = new File(DIRETORIO_ENTRADA_PADRAO.concat(nomeArquivoDetectado));

		if (Boolean.TRUE.equals(isArquivoValido(novoArquivo))) {

			List<Dado> dados = new ArrayList<>();
			LOGGER.log(Level.INFO, () -> "Extraindo dados do arquivo - " + nomeArquivoDetectado);
			List<String> linhas = Files.readLines(novoArquivo, StandardCharsets.UTF_8);

			Integer numeroLinha = null;
			try {
				
				for (String linha : linhas) {
					
					numeroLinha = linhas.indexOf(linha)+1;
					Dado dado = new ExtratorDadoFacade(new Linha(linha, numeroLinha)).extrair();
					dados.add(dado);
					
				}
				
				EscritorSaidaInformacaoVendas.escrever(nomeArquivoDetectado, new ProcessadorVendas(dados));
				LOGGER.log(Level.INFO, () -> "Extração concluída");
				
			} catch (
					LinhaArquivoInvalidaException | 
					ConversaoDadoException | 
					TipoDadoNaoEncontradoException | 
					DadoInvalidoException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
				EscritorSaidaInformacaoVendas.escreverErro(nomeArquivoDetectado, e.toString());
			}

		} else {
			LOGGER.log(Level.SEVERE, () -> "Arquivo inválido - " + nomeArquivoDetectado);
		}

		java.nio.file.Files.delete(novoArquivo.toPath());
	}

	public static Boolean isArquivoValido(File file) {
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		return file.exists() 
				&& file.isFile() 
				&& file.length() > BigDecimal.ZERO.intValue()
				&& mimeType.equals(MIMETYPE_ACEITO);
	}
}
