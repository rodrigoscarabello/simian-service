# Simian Service

Em um futuro distante, na cadeia de evolução, os símios e os humanos estão cada vez mais próximos.
Por esse motivo ficou muito difícil distinguir quem é humano e quem é símio. \
Esta API Rest é capaz de identificar se uma sequência de DNA pertence a um humano ou um símio. \
Um DNA símio será aquele que contiver mais de uma sequência de 4 letras iguais nas direções horizontais, verticais ou diagonais.

## Executando API em ambiente local
#### Recursos necessários:

  - Java 8
  - Docker
  - Docker Compose
  
  Executar os seguinte comandos, a partir da raiz da aplicação.
  ```
  $ ./mvnw package
  $ docker-compose up   
  ```

## Testando a API

- A API disponibiliza dois serviços `` /simian `` e `` /stats ``
- O serviço `` /simian `` deve receber uma sequência de DNA através de um HTTP POST com um JSON que contém o seguinte formato, exemplo:

	```
	{
		"dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
	}
	```
	- Caso a sequência seja de um símio, o serviço retornará HTTP Status 200-OK.
	- Caso contrário retornará HTTP Status 403-FORBIDDEN.
 
	###### Exemplo
	``` 
	$ curl -X POST -v \
		-d '{"dna":["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]}' \
		-H "Content-type: application/json"  \
	http://localhost:8080/simian
	```
- O serviço `` /stats `` responde com um Json que retorna as estatísticas de verificações de DNA através de um HTTP GET e deve informar a quantidade de DNA’s símios, quantidade de DNA’s humanos, e a proporção de símios para a população humana.

	###### Exemplo
	``` 
	$ curl http://localhost:8080/stats 
	```
	Resultado:
	``` 
	{
		"ratio": 0.4,
		"count_mutant_dna": 40, 
		"count_human_dna": 100
	}
    ```
## Google App Engine
API foi disponibilizada no Google App Engine e pode ser acessada atravéz das URL`s:

- http://35.190.153.29/simian
- http://35.190.153.29/stats

## Especificações da arquitetura utilizada
- IntelliJ IDEA;
- Spring Boot;
- Java 8;
- Maven;
- JUnit;
- Spring Boot Test;
- MongoDB;
- Docker;
- Docker Compose.