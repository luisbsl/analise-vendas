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

<div style="text-align:center"><img src="https://file.globalupload.io/tId21xsEL8.png" width="1024" /></div>

### Análise de Código - Sonarqube

<div style="text-align:center"><img src="https://file.globalupload.io/pNuQhzYgCx.png" width="1024" /></div>

### Cobertura de Testes - JaCoCo

```console
mvn clean test
```

Abrir arquivo /target/site/jacoco/index.html

<div style="text-align:center"><img src="https://file.globalupload.io/jBCEvvYECj.png	" width="1024" /></div>

## Diagramas de Classes

### Models

<div style="text-align:center"><img src="https://file.globalupload.io/8PYTmcFkAV.jpg	" width="1024" /></div>

### Builders

<div style="text-align:center"><img src="https://file.globalupload.io/ycKWBzbXYq.jpg" width="1024" /></div>

### Services

<div style="text-align:center"><img src="https://file.globalupload.io/TzGznhiwz8.jpg" width="1024" /></div>
