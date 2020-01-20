# Sistema de Análise de Vendas

Sistema para processamento de arquivos de vendas, extração das informações e geração de relatórios.

### Stack
* Java 8
* Maven
* Junit 5
* Guava
* JaCoCo
* Sonar

### Artefatos

* Executável: /bin/analise-vendas.jar
* Diagramas: /docs/diagramas
* Relatórios: /docs/relatórios
* Javadoc: /docs/javadoc/index.html


### Binário executável 

```console
java -jar bin/analise-vendas.jar
```

### Build

O build utiliza o plugin **shade** para empacotar o projeto junto com as dependências

```console
mvn clean package -DskipTests
```

### Executável

```console
java -jar target/analise-vendas-1.0.0-shaded.jar
```

## Javadoc

Acessar arquivo /docs/javadoc/index.html

<div style="text-align:center"><img src="https://ipfs.globalupload.io/QmZCRSrY7A85RtQVgQTqtDDvCE5JZYxCGgumTb5zwi826x" /></div>

### Análise de Código - Sonarqube

<div style="text-align:center"><img src="https://ipfs.globalupload.io/QmYapJeQYP87GJAaYxCaA6CHmkQm5fkHx1zwscLdRG2YCa" width="1024" /></div>

### Cobertura de Testes - JaCoCo

```console
mvn clean test
```

Abrir arquivo /target/site/jacoco/index.html

<div style="text-align:center"><img src="https://ipfs.globalupload.io/QmRiUpbnZNvotz5pMXanfRYtCdGzLsQ5BRuei6Puocqpkc	" width="1024" /></div>

## Diagramas de Classes

### Models

<div style="text-align:center"><img src="https://ipfs.globalupload.io/QmcTX7N8vFzsehv3QfQMP8R2D4QrsG3GD5oYEb4KDSbsqD" width="1024" /></div>

### Builders

<div style="text-align:center"><img src="https://ipfs.globalupload.io/QmXDNUbhufQxE92GXAs4iSFcF4fXp6FywKRR4cxxvwfQa5" width="1024" /></div>

### Services

<div style="text-align:center"><img src="https://ipfs.globalupload.io/QmcgSpdEpWDspxDb3X7YQQJ97b57WZ5xG4K7AxXx4iJEcz" width="1024" /></div>
