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
 * Serviço para leitura de diretório de arquivos com Dados de Vendas
 * Depois que entra em execução fica lendo constantemente o diretório especificado
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
	 * @see WatchService
	 * 
	 * @throws IOException exceção lançada caso não seja possível escutar o diretório
	 * @throws InterruptedException exceção lançada caso ocorra alguma exceção severa e impossível de tratar
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

	/**
	 * Executado toda vez que novos arquivos são detectados na pasta configurada como padrão
	 * Prossegue com a execução do ExtratorDadoFacade
	 * 
	 * Após a extração e conversão de todas as linhas do arquivo prossegue com a execução
	 * do serviço EscritorSaidaInformacaoVendas para escrita no arquivo físico de saída
	 *  
	 * Caso tenha ocorrido erros durante o processo de execução é chamada a execução do Serviço
	 * EscritorSaidaInformacaoVendas para escrita no arquivo físico de erro
	 * 
	 * Logo após deleta o arquivo de entrada
	 * 
	 * @see ExtratorDadoFacade#extrair()
	 * @see EscritorSaidaInformacaoVendas#escrever(String, ProcessadorVendas)
	 * @see EscritorSaidaInformacaoVendas#escreverErro(String, String)
	 * @param nomeArquivoDetectado nome do arquivo que foi inserido dentro da pasta
	 * @throws IOException exceção lançada caso não seja possível ler o arquivo
	 */
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
			EscritorSaidaInformacaoVendas.escreverErro(nomeArquivoDetectado, 
					"Arquivo inválido! "
					+ "\r\n - Deve ser do tipo texto "
					+ "\r\n - Não pode ser vazio");
		}

		java.nio.file.Files.delete(novoArquivo.toPath());
	}

	/**
	 * Para execução correta deste serviço é necessário que o arquivo tenha Mimetype text/plain
	 * não pode ser vazio e deverá ser um arquivo e não um diretório
	 * 
	 * @param file arquivo validação
	 * @return retorna True caso o arquivo contemple todos os requisitos
	 */
	public static Boolean isArquivoValido(File file) {
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		return file.exists() 
				&& file.isFile() 
				&& file.length() > BigDecimal.ZERO.intValue()
				&& mimeType.equals(MIMETYPE_ACEITO);
	}
}
