# Instruções básicas de execução – Guilda API

Este projeto é uma aplicação Java com Spring Boot, que utiliza JPA, PostgreSQL e Elasticsearch, todos executados via Docker.

## Pré-requisitos:

Antes de iniciar, é necessário ter instalado na máquina:

- Java 21
- Maven
- Docker

A aplicação depende de um banco de dados PostgreSQL e de um Elasticsearch, que são executados via Docker.

## Iniciar banco de dados PostgreSQL e o Elasticsearch:
- Acesse a pasta onde está o docker-compose.yaml:

```bash
cd ./docker
```

- Suba os containers:

```bash
docker-compose up -d 
```

- Verifique se os containers estão rodando:

```bash
docker ps
```
## Ajuste da senha do usuário postgres

A imagem do banco de dados utiliza uma senha padrão desconhecida para o usuário postgres.
Por isso, é necessário redefinir a senha manualmente.

Execute os comandos abaixo:

```bash
docker exec -it guilda-postgres bash 
```
- Dentro do container

```bash
psql -U postgres
ALTER USER postgres PASSWORD 'password';
```
A senha configurada deve ser a mesma definida no arquivo application.yaml da aplicação.

## Executando a aplicação GuildaAPI:

### Opcão 1 - Terminal

Observação:
É necessário que o JAVA_HOME esteja configurado para o JDK 21,
pois o projeto utiliza Spring Boot 4.

Para isso rode este comando:

No PowerShell:
```
$env:JAVA_HOME="C:\Program Files\Java\jdk-21"
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
```

Depois:

```
java -version
.\mvnw -v
```
Ambos precisam mostrar Java 21.

Agora para rodar:

```bash
.\mvnw clean package
```
Depois:
```bash
java -jar target/GuildaProject-0.0.1-SNAPSHOT.jar
```

### Opção 2 – Executar pela IDE (IntelliJ)

- Abra o projeto no IntelliJ
- Localize a classe principal GuildaProjectApplication
- Clique em Run 

## Executando os testes

### Opção 1 - Terminal

```bash
.\mvnw test
```

### Opção 2 – Executar pela IDE (IntelliJ)

- Abra o projeto no IntelliJ
- Localize a pasta test e o teste que deseja rodar
- Clique com o botão direito em cima do teste desejado
- run 'nome-do-teste'