# Desafio

## Tecnologias

Este projeto utiliza Java com Spring Boot, banco de dados PostgresSQL e para simular a AWS o Localstack.

Requisitos para rodar o sistema:

 - [Docker](https://docs.docker.com/get-docker/)
 - [Docker-compose](https://docs.docker.com/compose/install/)
 - [SdkMan](https://sdkman.io/install)  para facilitar a configuração
 - [Java 17](https://sdkman.io/usage)
 - [Gradle](https://sdkman.io/usage)

## Detalhes do projeto

Um sistema transacional dividido dois micro serviços, uma API e um ASYNC.

### Responsabilidade
 - **API**:  crud das contas e cadastro de novas transações.
 - **ASYNC**:  efetivação das transações e notificações ao usuário.

### Documentações

Na API temos os resources documentados pelo swagger, link para o mesmo: http://localhost:8080/swagger-ui.html

## Execução do projeto

Para compilar o projeto e subir todos os containers com os micro serviços e a infra, primeiramente precisamos do ip da maquina host e incluir na propertie **cloud.aws.server**, neste padrão http://IpDoHostAqui:4566 nos seguintes arquivos:
 - api/src/main/resources/application-docker.properties
 - async/src/main/resources/application-docker.properties

Após isto basta executar o arquivo abaixo e o projeto sera compilado e os containers montados

``` shell
    ./initEnvironment.sh
```

**Caso seu ambiente não seja linux com interface Gnome o comando gnome-terminal... na linha 10 do arquivo acima pode não funcionar, neste caso comente a linha e abra novas abas de terminal com os comandos contidos nele para visualização dos logs**

## Ambiente de desenvolvimento

Configurações de ambiente:
 - IDE [Intellij](https://www.jetbrains.com/idea/download/)

Alterar o arquivo **docker-compose.yml** comentando os serviços **api** e **async** e subir os containers do postgres e localtack com o comando abaixo

```shell
docker-compose up
```

Para a execução do projeto no IntelliJ não é necessária a configuração de profile.

Nos recursos da AWS, se faz necessária a configuração de uma conta com a accessKey=localstack, e secretKey=localstack

Para facilitar o uso do localstack é recomendado a utilização da lib [awscli-local](https://github.com/localstack/awscli-local)